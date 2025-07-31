package org.example.communityforumapp.service;
import org.example.communityforumapp.domain.User;
import org.example.communityforumapp.repository.CommunityRepository;
import org.example.communityforumapp.dto.CommunityData;
import org.example.communityforumapp.config.JWTService;
import org.example.communityforumapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



import java.util.*;

@Service    
public class UserService {
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final List<String> nicknames;
    private final CommunityRepository communityRepository;

    @Autowired
    public UserService(JWTService jwtService, UserRepository userRepository, CommunityRepository communityRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.nicknames = new ArrayList<>();
        this.communityRepository = communityRepository;
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

    public ResponseEntity<Boolean> isMember(String email, Long communityId) {
        boolean value = true;
        List<Long> ids = new ArrayList<>();
        Optional<CommunityData> communityData = communityRepository.findById(communityId);
        communityData.ifPresent(data -> ids.addAll(data.getUserIds()));

        Long id = userRepository.findByEmail(email).get().getId();

        if (ids.contains(id)) {
            return ResponseEntity.status(200).body(true);
        }
        else {
            return ResponseEntity.status(200).body(false);
        }

    }
}
