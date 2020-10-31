package com.virtuallab.executor;

import com.virtuallab.events.GenerateSubmissionTemplateEvent;
import com.virtuallab.submission.entrypoint.SubmissionTemplateRequest;
import com.virtuallab.submission.entrypoint.SubmissionTemplateService;
import com.virtuallab.task.entrypoint.InputParameterRequest;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenerateSubmissionTemplateEventHandler {

    private final SubmissionTemplateService templateService;

    public GenerateSubmissionTemplateEventHandler(SubmissionTemplateService templateService) {
        this.templateService = templateService;
    }

    @EventListener
    public void handle(GenerateSubmissionTemplateEvent event) {
        generateJavaTemplate(event);
    }

    private void generateJavaTemplate(GenerateSubmissionTemplateEvent event) {
        StringBuilder template = new StringBuilder();

        template.append("public class Submission{\n");
        template.append("public ").append(event.getOutputParameters()).append(" ").append(event.getMethodName()).append("("); // public String methodName(

        // first param for simplicity
        final InputParameterRequest firstParam = event.getInputParameters().get(0);
        template.append(firstParam.getType()).append(" ").append(firstParam.getName()); // public String methodName(Type1 name1

        // the rest of params
        final List<InputParameterRequest> theRest = event.getInputParameters().subList(1, event.getInputParameters().size());
        theRest.forEach(param -> {
            template.append(",").append(param.getType()).append(" ").append(param.getName()); // public String methodName(Type1 name1, Type2 name2
        });
        template.append("){\n\n}\n");
        template.append("}");

        templateService.create(new SubmissionTemplateRequest(event.getTaskId(), "java", template.toString()));
    }
}