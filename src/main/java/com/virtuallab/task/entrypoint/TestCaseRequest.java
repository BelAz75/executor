package com.virtuallab.task.entrypoint;

public class TestCaseRequest {

    private String inputData;
    private String expectedData;

    public TestCaseRequest() {
    }

    public TestCaseRequest(String inputData, String expectedData) {
        this.inputData = inputData;
        this.expectedData = expectedData;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public String getExpectedData() {
        return expectedData;
    }

    public void setExpectedData(String expectedData) {
        this.expectedData = expectedData;
    }

}
