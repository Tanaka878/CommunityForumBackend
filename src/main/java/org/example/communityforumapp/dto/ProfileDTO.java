package org.example.communityforumapp.dto;

import lombok.Data;
import lombok.ToString;
import org.example.communityforumapp.utils.Gender;

@Data
@ToString
public class ProfileDTO {
    String name;
    String email;
    String numberOfGroups;
    Gender gender;
    String Nickname;
    String dateJoined;
}
