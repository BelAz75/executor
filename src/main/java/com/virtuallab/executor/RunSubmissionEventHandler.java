package com.virtuallab.executor;

import com.virtuallab.common.Language;
import com.virtuallab.events.RunSubmissionEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RunSubmissionEventHandler {
    private final ContainerStarter starter;

    public RunSubmissionEventHandler(ContainerStarter starter) {
        this.starter = starter;
    }

    @EventListener
    public void handle(RunSubmissionEvent event) throws IOException, InterruptedException {
        Language language = Language.toEnum(event.getLanguage());
        switch (language) {
            case JAVA:
                starter.executeJavaCode(event.getId(), event.getTaskId(), event.getCode());
                break;
            case PYTHON:
                starter.executePythonCode(event.getId(), event.getTaskId(), event.getCode());
                break;
            case C:
                starter.executeCCode(event.getId(), event.getTaskId(), event.getCode());
                break;
            default:
                throw new RuntimeException("Programming language " + language + " not supported");
        }
    }

}
