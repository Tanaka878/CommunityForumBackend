package org.example.communityforumapp.auth;



import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

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
