package com.virtuallab.task.entrypoint;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class AssignTaskRequest {

    private LocalDateTime deadline;
    private Set<String> userIds = new HashSet<>();
    private Set<String> groupIds = new HashSet<>();

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Set<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<String> userIds) {
        this.userIds = userIds;
    }

    public Set<String> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(Set<String> groupIds) {
        this.groupIds = groupIds;
    }
}
