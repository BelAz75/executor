package com.virtuallab.task.entrypoint;

public class UserAssignmentResponse {

    private String id;

    public UserAssignmentResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
