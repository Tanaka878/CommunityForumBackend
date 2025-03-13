package org.example.communityforumapp.repository;

import org.example.communityforumapp.dto.CommunityData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityData, Long> {
    CommunityData findById(long id);
}
