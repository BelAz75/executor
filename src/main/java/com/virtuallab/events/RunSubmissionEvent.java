package com.virtuallab.events;

public class RunSubmissionEvent {
    private String id;
    private String language;
    private String code;

    public RunSubmissionEvent(String id, String language, String code) {
        this.id = id;
        this.language = language;
        this.code = code;
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
}
