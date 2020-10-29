package com.virtuallab.executor;

import com.virtuallab.events.GenerateSubmissionTemplateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GenerateSubmissionTemplateEventHandler {

    @EventListener
    public void handle(GenerateSubmissionTemplateEvent event) {
    }
}
