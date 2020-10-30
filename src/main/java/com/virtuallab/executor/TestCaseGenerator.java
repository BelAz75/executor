package com.virtuallab.executor;

import com.virtuallab.task.usecase.GetTaskInfo;
import com.virtuallab.task.usecase.TaskInfo;
import com.virtuallab.task.usecase.TaskParameterInfo;
import com.virtuallab.task.usecase.TaskTestCaseInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestCaseGenerator {

    private final GetTaskInfo taskInfo;

    public TestCaseGenerator(GetTaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    public void generateJavaTestClass() {
        TaskInfo taskInfo = this.taskInfo.execute("");
        if (taskInfo == null) return;
        StringBuilder testClassBuilder = new StringBuilder();
        testClassBuilder.append("public class TestCase {\n");
        TaskParameterInfo taskParameterInfo = taskInfo.getTaskParameters().get(0);
        List<TaskTestCaseInfo> testCases = taskInfo.getTaskTestCases();
        for (int i = 0; i < testCases.size(); i++) {
            TaskTestCaseInfo testCase = testCases.get(i);
            testClassBuilder.append("public void testCase" + (i+1) + "(){\n");
            testClassBuilder.append("Submission submission = new Submission();\n");
            if (taskParameterInfo.getOutputParameters().equals("String") ) {
                testClassBuilder.append("String result = submission." + taskParameterInfo.getMethodName() + "(");
                testClassBuilder.append(testCase.getInput());
                testClassBuilder.append(");\n");
                testClassBuilder.append("if (!result.equals(\"" + testCase.getOutput() +"\")) System.out.println(\"Failed test TestCase"+ (i + 1) +"\");");
            }
            testClassBuilder.append("}\n");
        }
        testClassBuilder.append("}");
    }
}
