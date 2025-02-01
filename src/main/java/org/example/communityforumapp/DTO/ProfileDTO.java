package org.example.communityforumapp.DTO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProfileDTO {
    String name;
    String email;
    String numberOfGroups;
    Gender gender;
    String Nickname;
}
