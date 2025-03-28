package org.example.communityforumapp.service;

import jakarta.transaction.Transactional;
import org.example.communityforumapp.repository.CommunityRepository;
import org.example.communityforumapp.dto.ChatDataDTO;
import org.example.communityforumapp.dto.ProfileDTO;
import org.example.communityforumapp.dto.CommunityData;
import org.example.communityforumapp.domain.User;
import org.example.communityforumapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        System.out.println("User has joined the community " + communityId + " with id " + id);
        // Fetch the community data by communityId
        CommunityData community = communityRepository.findById(communityId)
                .orElseThrow(() -> new IllegalArgumentException("Community not found"));

        //adding  the group to user
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            byId.get().getGroupIds().add(communityId);
            userRepository.save(byId.get());
        }
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

    public ResponseEntity<ChatDataDTO> isUserJoined(String email, Long groupId) {
        // Check if the user exists by email
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Check if the group exists by groupId
        Optional<CommunityData> communityData = communityRepository.findById(groupId);
        if (communityData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Check if the userId exists in the group's userIds
        Long userId = user.get().getId();
        boolean isUserInGroup = communityData.get().getUserIds().contains(userId);
        int size = communityData.get().getUserIds().size();

        ChatDataDTO chatDataDTO = new ChatDataDTO();
        chatDataDTO.setIsMember(isUserInGroup);  // Changed from setMember to setIsMember
        chatDataDTO.setUsersCount(size);

        return ResponseEntity.ok(chatDataDTO);
    }

    public ResponseEntity<String> getNumberOfGroups(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(user -> ResponseEntity.ok(String.valueOf(user.getGroupIds().size()))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"));
    }


    @Transactional
    public ResponseEntity<String> exitGroup(Long communityId, Long userId) {
        Optional<CommunityData> communityData = communityRepository.findById(communityId);
        Optional<User> user = userRepository.findById(userId);
        if (communityData.isPresent() && user.isPresent()) {
            //removing the communityId from the user group array
            user.get().getGroupIds().remove(communityId);
            userRepository.save(user.get());
            //removing the userId from the group
            communityData.get().getUserIds().remove(userId);
            communityRepository.save(communityData.get());
            return ResponseEntity.ok("User has exited the community " + communityId);

        }
        return ResponseEntity.internalServerError().body("Filed to exit  the community " + communityId);
    }

    public ResponseEntity<ProfileDTO> getProfileData(Long userId) {
        Optional<User> user= userRepository.findById(userId);
        if (user.isPresent()) {
           int groups =  user.get().getGroupIds().size();
            ProfileDTO profileDTO = new ProfileDTO();
            //populating  the DTO with the data
            profileDTO.setEmail(user.get().getEmail());
            profileDTO.setName(user.get().getFirstName());
            profileDTO.setGender(user.get().getGender());
            profileDTO.setNickname(user.get().getNickname());
            profileDTO.setNumberOfGroups(String.valueOf(groups));
            profileDTO.setDateJoined(String.valueOf(user.get().getLocalDate()));
            return ResponseEntity.ok(profileDTO);
        }
        else return ResponseEntity.notFound().build();
    }
}
