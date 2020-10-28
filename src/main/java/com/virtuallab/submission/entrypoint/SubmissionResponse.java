package com.virtuallab.submission.entrypoint;

import java.time.LocalDateTime;

public class SubmissionResponse {

    private String id;
    private LocalDateTime submittedAt;
    private String language;
    private String status;
    private String error;
    private int testsPassed;

    public SubmissionResponse(String id, LocalDateTime submittedAt, String language, String status, String error, int testsPassed) {
        this.id = id;
        this.submittedAt = submittedAt;
        this.language = language;
        this.status = status;
        this.error = error;
        this.testsPassed = testsPassed;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public String getLanguage() {
        return language;
    }

    public String getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public int getTestsPassed() {
        return testsPassed;
    }
}
