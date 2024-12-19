package org.example.communityforumapp.user;

import org.example.communityforumapp.config.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service    
public class UserService {
    private final JWTService jwtService;
    private final UserRepository userRepository;

    @Autowired
    public UserService(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }


    public ResponseEntity<User> findByEmail(String token) {
        String email = jwtService.extractUserName(token);
       User userdetails =  userRepository.findByEmail(email).get();
      boolean userIsValid =   jwtService.validateToken(token,userdetails);
        if (userIsValid) {
            return ResponseEntity.ok(userdetails);
        }else {
            return ResponseEntity.badRequest().body(null);
        }
        
    }
}
