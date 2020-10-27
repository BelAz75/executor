package com.virtuallab.task.usecase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskInfo {

    private String id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private List<TaskParameterInfo> taskParameters = new ArrayList<>();
    private List<TaskTestCaseInfo> taskTestCases = new ArrayList<>();

    public TaskInfo(String id, String title, String description, LocalDateTime createdAt) {
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

    public List<TaskParameterInfo> getTaskParameters() {
        return taskParameters;
    }

    public void setTaskParameters(List<TaskParameterInfo> taskParameters) {
        this.taskParameters = taskParameters;
    }

    public List<TaskTestCaseInfo> getTaskTestCases() {
        return taskTestCases;
    }

    public void setTaskTestCases(List<TaskTestCaseInfo> taskTestCases) {
        this.taskTestCases = taskTestCases;
    }
}
