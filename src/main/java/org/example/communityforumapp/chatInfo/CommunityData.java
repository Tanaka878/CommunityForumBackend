package org.example.communityforumapp.chatInfo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
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
