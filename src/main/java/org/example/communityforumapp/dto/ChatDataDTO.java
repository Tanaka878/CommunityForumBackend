package org.example.communityforumapp.dto;

import lombok.Getter;

public class ChatDataDTO {
    private boolean isMember;
    @Getter
    private int usersCount;
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