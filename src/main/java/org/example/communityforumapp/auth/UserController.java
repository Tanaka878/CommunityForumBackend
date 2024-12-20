package org.example.communityforumapp.auth;

import lombok.RequiredArgsConstructor;
import org.example.communityforumapp.user.User;
import org.example.communityforumapp.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RequiredArgsConstructor
@RequestMapping("/api/v1/demo-controller")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/greeting")
    public String greeting() {
        return "Hello World";
    }

    @GetMapping("/fetch")
    public ResponseEntity<User> getUserData(
            @RequestHeader("Authorization") String authorizationHeader) {
        // Ensure the token is valid and extract email
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(null); // Unauthorized
        }

        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        return userService.findByEmail(token);
    }

}
