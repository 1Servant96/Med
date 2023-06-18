package com.medcheck.service.serviceimpl;

import com.medcheck.config.jwt.JwtService;
import com.medcheck.db.entities.User;
import com.medcheck.db.repository.RoleRepository;
import com.medcheck.db.repository.UserRepository;
import com.medcheck.dto.LoginResponse;
import com.medcheck.dto.request.UserRequest;
import com.medcheck.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final RoleRepository roleRepository;

    @Override
    public LoginResponse login(UserRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        User user = userRepository.findByEmail(token.getName()).orElseThrow(() -> {
            throw new NotFoundException("the user with this email was not found");
        });
        if (request.getPassword() == null) {
            throw new NotFoundException("Password must not be empty");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("invalid password");
        }
        return new LoginResponse(jwtService.generateToken(user), user.getEmail(), roleRepository.findRoleByUserId(user.getId()).getNameOfRole());
    }
}
