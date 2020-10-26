package com.virtuallab.task.entrypoint;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.virtuallab.util.rest.StringDeserializer;

import java.util.ArrayList;
import java.util.List;

public class UpdateTaskRequest {

    @JsonDeserialize(using = StringDeserializer.class)
    private String title;
    @JsonDeserialize(using = StringDeserializer.class)
    private String description;
    private String methodName;
    private List<InputParameterRequest> inputParameters = new ArrayList<>();
    private String outputParameters;
    private List<TestCaseRequest> testCases = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<InputParameterRequest> getInputParameters() {
        return inputParameters;
    }

    public void setInputParameters(List<InputParameterRequest> inputParameters) {
        this.inputParameters = inputParameters;
    }

    public String getOutputParameters() {
        return outputParameters;
    }

    public void setOutputParameters(String outputParameters) {
        this.outputParameters = outputParameters;
    }

    public List<TestCaseRequest> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCaseRequest> testCases) {
        this.testCases = testCases;
    }
}
