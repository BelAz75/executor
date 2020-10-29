package com.virtuallab.task.dataprovider;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("user")
@Table(name = "task_assignment")
public class UserTaskAssignmentEntity extends TaskAssignmentEntity {

    @Column(name = "user_uuid")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
