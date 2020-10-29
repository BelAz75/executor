package com.virtuallab.task.usecase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskAssignment {

    private LocalDateTime deadline;
    private List<UserAssignment> users = new ArrayList<>();
    private List<GroupAssignment> groups = new ArrayList<>();

    public TaskAssignment() {
    }

    public TaskAssignment(LocalDateTime deadline, List<UserAssignment> users, List<GroupAssignment> groups) {
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

    public List<UserAssignment> getUsers() {
        return users;
    }

    public void setUsers(List<UserAssignment> users) {
        this.users = users;
    }

    public List<GroupAssignment> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupAssignment> groups) {
        this.groups = groups;
    }
}
