package com.medcheck.api;

import com.google.firebase.auth.FirebaseAuthException;
import com.medcheck.db.entities.User;
import com.medcheck.db.service.UserService;
import com.medcheck.dto.response.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@CrossOrigin( origins = "*")
@Tag(name = "User API", description = "User API")
public class UserApi {
    private final UserService userService;
    @GetMapping("/{userId}")
    @Operation(summary = "getUser", description = "Get user by id")
    public User getUser(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
    }

    @GetMapping("/getAllUsers")
    public List<User> getUsers (){
        return userService.getUsers();
    }

    @Operation(summary = "Suthorization with google", description = "You can register by google account")
    @PostMapping("/auth-google")
    public AuthenticationResponse authWithGoogle(String tokenId) throws FirebaseAuthException {
        return userService.authWithGoogle(tokenId);
    }

}
