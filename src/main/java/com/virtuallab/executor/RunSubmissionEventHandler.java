package com.virtuallab.executor;

import com.virtuallab.events.RunSubmissionEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RunSubmissionEventHandler {

    @EventListener
    public void handle(RunSubmissionEvent event) {
    }
}
