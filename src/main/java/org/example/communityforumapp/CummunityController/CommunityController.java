package org.example.communityforumapp.CummunityController;

import org.example.communityforumapp.CommunityRepo.CommunityRepository;
import org.example.communityforumapp.CommunityService.CommunityService;
import org.example.communityforumapp.chatInfo.CommunityData;
import org.example.communityforumapp.user.User;
import org.example.communityforumapp.user.UserRepository;
import org.example.communityforumapp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/communities")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommunityController {


    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    CommunityService communityService;

    @Autowired
    public CommunityController(CommunityRepository communityRepository,
                               UserRepository userRepository,
                               CommunityService communityService, UserService userService) {
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
        this.communityService = communityService;
        this.userService = userService;
    }

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
    public ResponseEntity<List<CommunityData>> getCommunities(@PathVariable String email) {
        return communityService.getMyCommunities(email);
    }

    @GetMapping("/isMember/{email}/{groupId}")
    public ResponseEntity<String> getMembers(@PathVariable String email, @PathVariable Long groupId) {
        return communityService.isUserJoined(email,groupId);

    }

    @GetMapping("/getNicknames")
    public ResponseEntity<List<String>> getNicknames() {
        return userService.findNicknames();
    }

    @GetMapping("/getNickname/{id}")
    public ResponseEntity<String> getNickname(@PathVariable Long id) {
        return userService.getNickName(id);
    }
}
