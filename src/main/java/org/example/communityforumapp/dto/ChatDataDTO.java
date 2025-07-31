package org.example.communityforumapp.dto;

import lombok.Data;


@Data
public class ChatDataDTO {
    private boolean isMember;
    private int usersCount;

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }

    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public void setIsMember(boolean isUserInGroup) {
        isMember = isUserInGroup;
    }
}