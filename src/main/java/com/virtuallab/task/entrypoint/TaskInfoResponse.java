package com.virtuallab.task.entrypoint;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskInfoResponse {
    private String id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private List<TaskParameterInfoResponse> taskParameters = new ArrayList<>();
    private List<TaskTestCaseInfoResponse> taskTestCases = new ArrayList<>();

    public TaskInfoResponse(String id, String title, String description, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<TaskParameterInfoResponse> getTaskParameters() {
        return taskParameters;
    }

    public void setTaskParameters(List<TaskParameterInfoResponse> taskParameters) {
        this.taskParameters = taskParameters;
    }

    public List<TaskTestCaseInfoResponse> getTaskTestCases() {
        return taskTestCases;
    }

    public void setTaskTestCases(List<TaskTestCaseInfoResponse> taskTestCases) {
        this.taskTestCases = taskTestCases;
    }
}
