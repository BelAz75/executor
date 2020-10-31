package com.virtuallab.submission.entrypoint;

import java.time.LocalDateTime;

public class SubmissionResponse {

    private String id;
    private LocalDateTime submittedAt;
    private String language;
    private String status;
    private String message;
    private int testsPassed;
    private int testsFailed;

    public SubmissionResponse(String id, LocalDateTime submittedAt, String language, String status, String message, int testsPassed,
                              int testsFailed) {
        this.id = id;
        this.submittedAt = submittedAt;
        this.language = language;
        this.status = status;
        this.message = message;
        this.testsPassed = testsPassed;
        this.testsFailed = testsFailed;
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

    public String getMessage() {
        return message;
    }

    public int getTestsPassed() {
        return testsPassed;
    }

    public int getTestsFailed() {
        return testsFailed;
    }
}
