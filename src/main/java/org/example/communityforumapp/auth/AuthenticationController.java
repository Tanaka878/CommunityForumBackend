package org.example.communityforumapp.auth;

import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import com.nimbusds.openid.connect.sdk.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse1> register(@RequestBody RegisterRequest user) {

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest1 authenticationRequest) {

    }
}
