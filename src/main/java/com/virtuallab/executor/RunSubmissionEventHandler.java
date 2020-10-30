package com.virtuallab.executor;

import com.virtuallab.common.Language;
import com.virtuallab.events.RunSubmissionEvent;
import com.virtuallab.submission.dataprovider.SubmissionRepository;
import com.virtuallab.submission.entrypoint.SubmissionResult;
import com.virtuallab.submission.usecase.UpdateSubmission;
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
                starter.executeJavaCode(event.getId(), event.getCode());
                break;
            case PYTHON:
                starter.executePythonCode(event.getId(), event.getCode());
                break;
            case C:
                starter.executeCCode(event.getId(), event.getCode());
                break;
            default:
                throw new RuntimeException("Programming language " + language + " not supported");
        }
    }
}
