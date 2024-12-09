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

        /// TODO body of the function

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AutheticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
        return ResponseEntity.ofNullable(authenticationService.authenticate(authenticationRequest));
    }



}
