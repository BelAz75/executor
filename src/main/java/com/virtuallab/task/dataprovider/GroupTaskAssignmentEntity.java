package com.virtuallab.task.dataprovider;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("group")
@Table(name = "task_assignment")
public class GroupTaskAssignmentEntity extends TaskAssignmentEntity {

    @Column(name = "group_uuid")
    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

}
