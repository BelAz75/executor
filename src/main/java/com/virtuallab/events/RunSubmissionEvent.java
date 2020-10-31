package com.virtuallab.events;

public class RunSubmissionEvent {
    private String id;
    private String taskId;
    private String language;
    private String code;

    public RunSubmissionEvent(String id, String taskId, String language, String code) {
        this.id = id;
        this.code = code;
        this.taskId = taskId;
        this.language = language;
    }

    public String getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public String getCode() {
        return code;
    }

    public String getTaskId() {
        return taskId;
    }
}
