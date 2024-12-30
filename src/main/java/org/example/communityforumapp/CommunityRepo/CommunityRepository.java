package org.example.communityforumapp.CommunityRepo;

import org.example.communityforumapp.chatInfo.CommunityData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<CommunityData, Long> {
}
