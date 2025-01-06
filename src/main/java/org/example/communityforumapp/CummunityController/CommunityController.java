package org.example.communityforumapp.CummunityController;

import org.example.communityforumapp.CommunityRepo.CommunityRepository;
import org.example.communityforumapp.CommunityService.CommunityService;
import org.example.communityforumapp.chatInfo.CommunityData;
import org.example.communityforumapp.user.User;
import org.example.communityforumapp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/communities")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommunityController {

    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private UserRepository userRepository;
    CommunityService communityService;

    @PostMapping("/createCommunity")
    public ResponseEntity<CommunityData> createCommunity(@RequestBody CommunityData community) {
        // Save the community object to the database
        CommunityData savedCommunity = communityRepository.save(community);

        // Return the saved object along with HTTP 201 status
        return ResponseEntity.status(201).body(savedCommunity);
    }

    @PostMapping("/join/{email}/{communityId}")
    public ResponseEntity<CommunityData> joinCommunity(@PathVariable String email,@PathVariable Long communityId) {
        Optional<User> findUser = userRepository.findByEmail(email);
        if (findUser.isPresent()) {
            return communityService.join(findUser.get().getId(), communityId);
        }
        else return ResponseEntity.status(404).body(null);

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CommunityData>> getAll() {
        List<CommunityData> list = communityRepository.findAll();
        return ResponseEntity.status(200).body(list);
    }

    @RequestMapping("/getCommunities/{email}")
    public ResponseEntity<List<CommunityData>> getMycommunities(@PathVariable String email) {
        return communityService.getMyCommunities(email);
    }
}
