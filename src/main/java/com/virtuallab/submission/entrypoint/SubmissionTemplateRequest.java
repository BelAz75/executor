package com.virtuallab.submission.entrypoint;

public class SubmissionTemplateRequest {
    private String taskId;
    private String language;
    private String templateCode;

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getLanguage() {
        return language;
    }

    public String getTemplateCode() {
        return templateCode;
    }
}
