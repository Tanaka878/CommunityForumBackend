package org.example.communityforumapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.communityforumapp.utils.Gender;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDTO {
    String name;
    String email;
    String numberOfGroups;
    Gender gender;
    String Nickname;
    String dateJoined;
}
