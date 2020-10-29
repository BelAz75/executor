package com.virtuallab.task.entrypoint;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskAssignmentResponse {

    private LocalDateTime deadline;
    private List<UserAssignmentResponse> users = new ArrayList<>();
    private List<GroupAssignmentResponse> groups = new ArrayList<>();

    public TaskAssignmentResponse(LocalDateTime deadline, List<UserAssignmentResponse> users, List<GroupAssignmentResponse> groups) {
        this.deadline = deadline;
        this.users = users;
        this.groups = groups;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public List<UserAssignmentResponse> getUsers() {
        return users;
    }

    public void setUsers(List<UserAssignmentResponse> users) {
        this.users = users;
    }

    public List<GroupAssignmentResponse> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupAssignmentResponse> groups) {
        this.groups = groups;
    }
}
