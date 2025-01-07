package org.example.communityforumapp.CommunityService;

import jakarta.transaction.Transactional;
import org.example.communityforumapp.CommunityRepo.CommunityRepository;
import org.example.communityforumapp.chatInfo.CommunityData;
import org.example.communityforumapp.user.User;
import org.example.communityforumapp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommunityService {

    private final UserRepository userRepository;
    CommunityRepository communityRepository;


    @Autowired
    public CommunityService(CommunityRepository communityRepository, UserRepository userRepository) {
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<CommunityData> join(Long id, Long communityId) {
        System.out.println("Uuser has joined the community " + communityId + " with id " + id);
        // Fetch the community data by communityId
        CommunityData community = communityRepository.findById(communityId)
                .orElseThrow(() -> new IllegalArgumentException("Community not found"));

        //adding  the group to user
        Optional<User> byId = userRepository.findById(id);
        byId.get().getGroupIds().add(communityId);
        userRepository.save(byId.get());
        System.out.println("User has joined the community " + communityId + " with id " + id);


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

    public ResponseEntity<List<CommunityData>> getMyCommunities(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email " + email + " not found"));


        List<CommunityData> communities = user.getGroupIds().stream()
                .map(groupId -> communityRepository.findById(groupId)
                        .orElseThrow(() -> new IllegalArgumentException("Community with ID " + groupId + " not found")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(communities);
    }

}
