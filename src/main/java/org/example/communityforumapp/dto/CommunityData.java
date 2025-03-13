package org.example.communityforumapp.dto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
public class CommunityData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String communityName;
    private String communityDescription;
    private int communitySize;
    @ElementCollection
    private List<Long> userIds = new ArrayList<>();
}
