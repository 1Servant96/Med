package com.medcheck.db.service;

import com.medcheck.dto.request.RegisterRequest;
import com.medcheck.dto.request.UserRequest;
import com.medcheck.dto.response.AuthResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

    AuthResponse login(UserRequest request);

    AuthResponse registration(RegisterRequest registerRequest);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
