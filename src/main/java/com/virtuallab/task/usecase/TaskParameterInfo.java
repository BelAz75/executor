package com.virtuallab.task.usecase;

import java.util.List;

public class TaskParameterInfo {

    private String methodName;
    private String language;
    private List<InputParameterInfo> inputParameters;
    private String outputParameters;

    public TaskParameterInfo(String methodName, String language, List<InputParameterInfo> inputParameters, String outputParameters) {
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

    public List<InputParameterInfo> getInputParameters() {
        return inputParameters;
    }

    public void setInputParameters(List<InputParameterInfo> inputParameters) {
        this.inputParameters = inputParameters;
    }

    public String getOutputParameters() {
        return outputParameters;
    }

    public void setOutputParameters(String outputParameters) {
        this.outputParameters = outputParameters;
    }
}
