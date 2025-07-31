package org.example.communityforumapp.controller;



import lombok.RequiredArgsConstructor;
import org.example.communityforumapp.dto.AuthenticationRequest;
import org.example.communityforumapp.service.AuthenticationService;
import org.example.communityforumapp.dto.AutheticationResponse;
import org.example.communityforumapp.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component

@RequestMapping("/api/v1/auth")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/register/")
    public ResponseEntity<AutheticationResponse> register(@RequestBody RegisterRequest registerRequest) {

        System.out.println("Endpoint Hit");
        return ResponseEntity.ok(authenticationService.register(registerRequest));



    }

    @PostMapping("/authenticate/")
    public ResponseEntity<AutheticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        AutheticationResponse response = authenticationService.authenticate(authenticationRequest);
        return ResponseEntity.ok(response); // Return a 200 OK response with the token
    }








}
