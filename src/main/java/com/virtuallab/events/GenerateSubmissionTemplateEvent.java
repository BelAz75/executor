package com.virtuallab.events;

import com.virtuallab.task.entrypoint.InputParameterRequest;
import com.virtuallab.task.entrypoint.TestCaseRequest;

import java.util.List;

public class GenerateSubmissionTemplateEvent {
    private String taskId;
    private String methodName;
    private List<InputParameterRequest> inputParameters;
    private String outputParameters;
    private List<TestCaseRequest> testCases;

    public GenerateSubmissionTemplateEvent(String taskId, String methodName, List<InputParameterRequest> inputParameters, String outputParameters, List<TestCaseRequest> testCases) {
        this.taskId = taskId;
        this.methodName = methodName;
        this.inputParameters = inputParameters;
        this.outputParameters = outputParameters;
        this.testCases = testCases;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<InputParameterRequest> getInputParameters() {
        return inputParameters;
    }

    public String getOutputParameters() {
        return outputParameters;
    }

    public List<TestCaseRequest> getTestCases() {
        return testCases;
    }
}
