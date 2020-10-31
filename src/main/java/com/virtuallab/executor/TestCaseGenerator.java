package com.virtuallab.executor;

import com.virtuallab.events.RunSubmissionEvent;
import com.virtuallab.events.TestRunnerEvent;
import com.virtuallab.task.dataprovider.TaskTestRunnerEntity;
import com.virtuallab.task.dataprovider.TaskTestRunnerRepository;
import com.virtuallab.task.usecase.GetTaskInfo;
import com.virtuallab.task.usecase.TaskInfo;
import com.virtuallab.task.usecase.TaskParameterInfo;
import com.virtuallab.task.usecase.TaskTestCaseInfo;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class TestCaseGenerator {
    private final GetTaskInfo taskInfo;
    private final TaskTestRunnerRepository testRunnerRepository;

    public TestCaseGenerator(GetTaskInfo taskInfo, TaskTestRunnerRepository testRunnerRepository) {
        this.taskInfo = taskInfo;
        this.testRunnerRepository = testRunnerRepository;
    }

    @EventListener
    public void handle(TestRunnerEvent event) {
        TaskInfo taskInfo = this.taskInfo.execute(event.getTaskId());
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
                testClassBuilder.append("if (!result.equals(" + testCase.getOutput() +")) System.out.println(\"Failed TestCase"+ (i + 1) +"\");");
                testClassBuilder.append("else System.out.println(\"Passed TestCase"+ (i + 1) +"\");");
            }
            testClassBuilder.append("}\n");
        }
        testClassBuilder.append("public static void main(String[] args) {\n");
        testClassBuilder.append("TestCase testCase = new TestCase();\n");
        for (int i = 0; i < testCases.size(); i++) {
            testClassBuilder.append("testCase.testCase" + (i+1) + "();\n");
        }
        testClassBuilder.append("}\n");
        testClassBuilder.append("}");

        TaskTestRunnerEntity testRunnerEntity = new TaskTestRunnerEntity();
        testRunnerEntity.setTaskId(event.getTaskId());
        testRunnerEntity.setLanguage(event.getLanguage());
        testRunnerEntity.setTaskTestCode(testClassBuilder.toString());
        testRunnerRepository.save(testRunnerEntity);
    }
}
