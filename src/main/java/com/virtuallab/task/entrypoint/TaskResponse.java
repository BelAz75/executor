package com.virtuallab.task.entrypoint;

public class TaskResponse {
    private String id;

    public TaskResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
