package com.medcheck.db.service.serviceimpl;

import com.medcheck.config.jwt.JwtService;
import com.medcheck.db.entities.Role;
import com.medcheck.db.entities.User;
import com.medcheck.db.repository.UserRepository;
import com.medcheck.db.service.UserService;
import com.medcheck.dto.request.RegisterRequest;
import com.medcheck.dto.request.UserRequest;
import com.medcheck.dto.response.AuthResponse;
import com.medcheck.exceptions.BadCredentialsException;
import com.medcheck.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

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

}
