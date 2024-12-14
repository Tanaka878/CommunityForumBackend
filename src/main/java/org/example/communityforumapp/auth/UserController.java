package org.example.communityforumapp.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RequiredArgsConstructor
@RequestMapping("/api/v1/demo-controller")
@RestController
public class UserController {

    @GetMapping("/greeting")
    public String greeting() {
        return "Hello World";
    }

}
