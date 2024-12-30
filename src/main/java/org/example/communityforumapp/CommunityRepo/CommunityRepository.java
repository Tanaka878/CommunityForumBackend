package org.example.communityforumapp.CommunityRepo;

import org.example.communityforumapp.chatInfo.CommunityData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityData, Long> {
    CommunityData findById(long id);
}
