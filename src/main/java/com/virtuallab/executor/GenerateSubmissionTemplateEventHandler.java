package com.virtuallab.executor;

import com.virtuallab.events.GenerateSubmissionTemplateEvent;
import com.virtuallab.submission.entrypoint.SubmissionTemplateRequest;
import com.virtuallab.submission.entrypoint.SubmissionTemplateService;
import com.virtuallab.submission.usecase.DeleteSubmissionTemplate;
import com.virtuallab.task.entrypoint.InputParameterRequest;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GenerateSubmissionTemplateEventHandler {

    private final SubmissionTemplateService templateService;
    private final DeleteSubmissionTemplate deleteSubmissionTemplate;

    public GenerateSubmissionTemplateEventHandler(SubmissionTemplateService templateService, DeleteSubmissionTemplate deleteSubmissionTemplate) {
        this.templateService = templateService;
        this.deleteSubmissionTemplate = deleteSubmissionTemplate;
    }

    @EventListener
    public void handle(GenerateSubmissionTemplateEvent event) {
        // to be safe when task is updated
        deleteSubmissionTemplate.execute(event.getTaskId());
        generateJavaTemplate(event);
        generatePythonTemplate(event);
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

    private void generatePythonTemplate(GenerateSubmissionTemplateEvent event) {
        String template = "" +
                "class Submission:\n\n" +
                "    def ${method_name}(self, ${input_params}):\n" +
                "        \n";

        String input_params = event.getInputParameters().stream().map(InputParameterRequest::getName).collect(Collectors.joining(", "));
        Map<String, String> substitutions = new HashMap<>();
        substitutions.put("input_params", input_params);
        substitutions.put("method_name", event.getMethodName());
        templateService.create(new SubmissionTemplateRequest(event.getTaskId(), "python", StrSubstitutor.replace(template, substitutions)));
    }
}