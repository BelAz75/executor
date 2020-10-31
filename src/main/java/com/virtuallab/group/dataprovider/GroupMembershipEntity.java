package com.virtuallab.group.dataprovider;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GroupMembershipEntity {

    @Column(name = "user_uuid")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
