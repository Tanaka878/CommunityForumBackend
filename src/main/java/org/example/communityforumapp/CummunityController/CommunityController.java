package org.example.communityforumapp.CummunityController;

import org.example.communityforumapp.CommunityRepo.CommunityRepository;
import org.example.communityforumapp.chatInfo.CommunityData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/communities")
public class CommunityController {

    @Autowired
    private CommunityRepository communityRepository;

    @PostMapping
    public ResponseEntity<CommunityData> createCommunity(@RequestBody CommunityData community) {
        // Save the community object to the database
        CommunityData savedCommunity = communityRepository.save(community);

        // Return the saved object along with HTTP 201 status
        return ResponseEntity.status(201).body(savedCommunity);
    }
}
