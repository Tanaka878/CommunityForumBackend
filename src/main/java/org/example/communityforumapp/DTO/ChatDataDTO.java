package org.example.communityforumapp.DTO;

import lombok.Getter;

public class ChatDataDTO {
    private boolean isMember;  // Changed from member to isMember to match frontend
    @Getter
    private int usersCount;

    // Getters and setters
    public boolean getIsMember() {
        return isMember;
    }

    public void setIsMember(boolean isMember) {
        this.isMember = isMember;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }
}