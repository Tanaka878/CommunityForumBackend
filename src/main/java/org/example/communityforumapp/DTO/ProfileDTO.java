package org.example.communityforumapp.DTO;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class ProfileDTO {
    String name;
    String email;
    String numberOfGroups;
    Gender gender;
    String Nickname;
    LocalDate dateJoined;
}
