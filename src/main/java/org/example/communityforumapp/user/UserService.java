package org.example.communityforumapp.user;

import org.example.communityforumapp.config.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service    
public class UserService {
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final List<String> nicknames;

    @Autowired
    public UserService(JWTService jwtService, UserRepository userRepository, List<String> nicknames) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.nicknames = nicknames;
    }


    public ResponseEntity<User> findByEmail(String token) {
        try {
            String email = jwtService.extractUserName(token); // Extract email from token
            User userDetails = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

            boolean userIsValid = jwtService.validateToken(token, userDetails);
            if (userIsValid) {
                System.out.println("User is valid");
                return ResponseEntity.ok(userDetails);
            } else {
                return ResponseEntity.status(403).body(null); // Forbidden
            }
        } catch (Exception e) {

            return ResponseEntity.status(500).body(null); // Internal Server Error
        }
    }

    public ResponseEntity<List<String>> findNicknames() {
        userRepository.findAll().forEach(user -> nicknames.add(user.getNickname()));
        return ResponseEntity.ok(nicknames);
    }

}
