package com.medcheck.db.service.serviceimpl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.medcheck.config.jwt.JwtService;
import com.medcheck.db.entities.Role;
import com.medcheck.db.entities.User;
import com.medcheck.db.repository.RoleRepository;
import com.medcheck.db.repository.UserRepository;
import com.medcheck.db.service.UserService;
import com.medcheck.dto.request.RegisterRequest;
import com.medcheck.dto.request.UserRequest;
import com.medcheck.dto.response.AuthResponse;
import com.medcheck.dto.response.AuthenticationResponse;
import com.medcheck.exceptions.BadCredentialsException;
import com.medcheck.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final RoleRepository roleRepository;

    @Override
    public AuthResponse login(UserRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getFirstName() + " " + request.getLastName(), request.getPassword());
        User user = userRepository.findByEmail(token.getName()).orElseThrow(() -> {
            throw new NotFoundException("the user with this name was not found");
        });
        if (request.getPassword() == null) {
            throw new NotFoundException("Password must not be empty");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("invalid password");
        }
        return new AuthResponse(jwtService.generateToken(user), user.getFirstName() + " " + user.getLastName(), userRepository.findRoleByUserEmail(user.getUsername()).getRoleName());
    }

    @Override
    public AuthResponse registration(RegisterRequest registerRequest) {
        User user = new User(registerRequest.getPassword(), registerRequest.getFirstName(), registerRequest.getLastName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(new Role("USER"));
        userRepository.save(user);
        return new AuthResponse(jwtService.generateToken(user), user.getFirstName() + " " + user.getLastName(), user.getRole().getRoleName());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("not found name"));
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }
    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    @Override
    public AuthenticationResponse authWithGoogle(String tokenId) throws FirebaseAuthException {
        FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(tokenId);
        if (!userRepository.existsByEmail(firebaseToken.getEmail())) {
            User newUser = new User();
            String[] name = firebaseToken.getName().split(" ");
            newUser.setFirstName(name[0]);
            newUser.setLastName(name[1]);
            newUser.setEmail(firebaseToken.getEmail());
            newUser.setPassword(firebaseToken.getEmail());
            newUser.setRole(roleRepository.getById(2L));

            userRepository.save(newUser);
        }
        User user = userRepository.findByEmail(firebaseToken.getEmail()).orElseThrow(() -> {
            throw new NotFoundException(String.format("Пользователь с таким электронным адресом %s не найден!", firebaseToken.getEmail()));
        });
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token, user.getRole().getRoleName(), user.getEmail());
    }

}
