package org.example.communityforumapp.CummunityController;

import org.example.communityforumapp.CommunityRepo.CommunityRepository;
import org.example.communityforumapp.CommunityService.CommunityService;
import org.example.communityforumapp.chatInfo.CommunityData;
import org.example.communityforumapp.user.User;
import org.example.communityforumapp.user.UserRepository;
import org.example.communityforumapp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        CommunityData savedCommunity = communityRepository.save(community);
        return ResponseEntity.status(201).body(savedCommunity);
    }

    @PostMapping("/isMember/{email}/{communityId}")
    public ResponseEntity<Boolean> isMember(@PathVariable String email, @PathVariable Long communityId) {
        return userService.isMember(email,communityId);
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
    public ResponseEntity<Map<String,String>> getNickname(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            System.out.println("User: " + user.get().getNickname());
            String nickname = user.get().getNickname();
            Map<String, String> response = new HashMap<>();
            response.put("nickname", nickname);
            System.out.println("The nickname is " + nickname);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found"));
        }
    }

    @RequestMapping("/numberOfGroups/{id}")
    public ResponseEntity<String> getNumberOfGroups(@PathVariable Long id) {
        return communityService.getNumberOfGroups(id);
    }

    @RequestMapping("/exitGroup/{communityId}/{userId}")
    public ResponseEntity<String> exitGroup(@PathVariable Long communityId, @PathVariable Long userId) {
        return communityService.exitGroup(communityId,userId);
    }


    RequestMapping("/getProfileData")
    public ResponseEntity<String> getProfileData(@PathVariable Long userId) {
        return communityService.getProfileData(userId);
        }


}
