package org.example.communityforumapp.CommunityService;

import org.example.communityforumapp.CommunityRepo.CommunityRepository;
import org.example.communityforumapp.chatInfo.CommunityData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommunityService {

    CommunityRepository communityRepository;


    @Autowired
    public CommunityService(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    public ResponseEntity<CommunityData> join(Long id, Long communityId) {
        // Fetch the community data by communityId
        CommunityData community = communityRepository.findById(communityId)
                .orElseThrow(() -> new IllegalArgumentException("Community not found"));

        // Get the existing list of user IDs
        List<Long> userIds = community.getUserIds();

        // Check if the user ID is already in the list to prevent duplicates
        if (!userIds.contains(id)) {
            // Append the new user ID to the list
            userIds.add(id);
            community.setUserIds(userIds); // Set the updated list back to the community entity
            communityRepository.save(community); // Save the updated community data
        }

        return ResponseEntity.ok(community);
    }

}
