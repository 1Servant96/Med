package com.medcheck.db.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.medcheck.db.entities.User;
import com.medcheck.dto.request.RegisterRequest;
import com.medcheck.dto.request.UserRequest;
import com.medcheck.dto.response.AuthResponse;
import com.medcheck.dto.response.AuthenticationResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {

    AuthResponse login(UserRequest request);

    AuthResponse registration(RegisterRequest registerRequest);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    List<User> getUsers();
    void deleteUserById(Long id);
    User getUserById(Long id);

    AuthenticationResponse authWithGoogle(String tokenId) throws FirebaseAuthException;
}
