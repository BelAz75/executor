package com.virtuallab.task.entrypoint;

import java.util.List;

public class TaskParameterInfoResponse {

    private String methodName;
    private String language;
    private List<InputParameterInfoResponse> inputParameters;
    private String outputParameters;

    public TaskParameterInfoResponse(String methodName, String language, List<InputParameterInfoResponse> inputParameters, String outputParameters) {
        this.methodName = methodName;
        this.language = language;
        this.inputParameters = inputParameters;
        this.outputParameters = outputParameters;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<InputParameterInfoResponse> getInputParameters() {
        return inputParameters;
    }

    public void setInputParameters(List<InputParameterInfoResponse> inputParameters) {
        this.inputParameters = inputParameters;
    }

    public String getOutputParameters() {
        return outputParameters;
    }

    public void setOutputParameters(String outputParameters) {
        this.outputParameters = outputParameters;
    }
}
