package com.virtuallab.submission.entrypoint;

public class SubmissionTemplateResponse {
    private String taskId;
    private String language;
    private String code;

    public SubmissionTemplateResponse(String taskId, String language, String code) {
        this.taskId = taskId;
        this.language = language;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getLanguage() {
        return language;
    }
}
