package org.example.communityforumapp.auth;

import lombok.RequiredArgsConstructor;
import org.example.communityforumapp.user.User;
import org.example.communityforumapp.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RequiredArgsConstructor
@RequestMapping("/api/v1/demo-controller")
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/greeting")
    public String greeting() {
        return "Hello World";
    }

    @GetMapping("/fetch")
    public ResponseEntity<User> getUserData(
            @RequestHeader("Authorization") String token) {


        return userService.findByEmail(token);
    }

}
