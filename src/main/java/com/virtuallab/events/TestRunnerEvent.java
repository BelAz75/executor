package com.virtuallab.events;

public class TestRunnerEvent {
    private String taskId;
    private String language;

    public TestRunnerEvent(String taskId, String language) {
        this.taskId = taskId;
        this.language = language;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getLanguage() {
        return language;
    }
}
