package org.example.communityforumapp.auth;

import lombok.RequiredArgsConstructor;
import org.example.communityforumapp.config.JWTService;
import org.example.communityforumapp.user.Role;
import org.example.communityforumapp.user.User;
import org.example.communityforumapp.user.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;


    public AutheticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        // Correct the order of parameters: username first, password second
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        );

        var user = userRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtService.generateToken(user);
        return AutheticationResponse.builder()
                .token(token)
                .build();
    }

    public AutheticationResponse register(RegisterRequest registerRequest) {

        Optional<User> user1 = userRepository.findByEmail(registerRequest.getEmail());
        if(user1.isPresent()) {
            throw new IllegalArgumentException("User already exists with the provided email."); // Or a custom exception
        }

        var user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .username(registerRequest.getEmail())
                .nickname(registerRequest.getNickname())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return AutheticationResponse.builder()
                .token(token).build();
    }
}
