package com.virtuallab.executor;

import com.virtuallab.common.Language;
import com.virtuallab.events.TestRunnerEvent;
import com.virtuallab.task.dataprovider.TaskTestRunnerEntity;
import com.virtuallab.task.dataprovider.TaskTestRunnerRepository;
import com.virtuallab.task.usecase.GetTaskInfo;
import com.virtuallab.task.usecase.TaskInfo;
import com.virtuallab.task.usecase.TaskParameterInfo;
import com.virtuallab.task.usecase.TaskTestCaseInfo;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TestCaseGenerator {
    private final GetTaskInfo getTaskInfo;
    private final TaskTestRunnerRepository testRunnerRepository;
    private final DeleteTestRunner deleteTestRunner;

    public TestCaseGenerator(GetTaskInfo getTaskInfo, TaskTestRunnerRepository testRunnerRepository, DeleteTestRunner deleteTestRunner) {
        this.getTaskInfo = getTaskInfo;
        this.testRunnerRepository = testRunnerRepository;
        this.deleteTestRunner = deleteTestRunner;
    }

    @EventListener
    public void handle(TestRunnerEvent event) {
        TaskInfo taskInfo = this.getTaskInfo.execute(event.getTaskId());
        if (taskInfo == null) return;
        // to be safe when task is being updated
        deleteTestRunner.delete(taskInfo.getId());
        TaskTestRunnerEntity javaTestRunnerEntity = new TaskTestRunnerEntity();
        javaTestRunnerEntity.setTaskId(event.getTaskId());
        javaTestRunnerEntity.setLanguage(Language.JAVA.name());
        StringBuilder javaClassBuilder = new StringBuilder();
        appendJavaCode(javaClassBuilder, taskInfo);
        javaTestRunnerEntity.setTaskTestCode(javaClassBuilder.toString());
        testRunnerRepository.save(javaTestRunnerEntity);

        TaskTestRunnerEntity pythonTestRunnerEntity = new TaskTestRunnerEntity();
        pythonTestRunnerEntity.setTaskId(event.getTaskId());
        pythonTestRunnerEntity.setLanguage(Language.PYTHON.name());
        StringBuilder pythonClassBuilder = new StringBuilder();
        appendPythonCode(pythonClassBuilder, taskInfo);
        pythonTestRunnerEntity.setTaskTestCode(pythonClassBuilder.toString());
        testRunnerRepository.save(pythonTestRunnerEntity);
    }

    private void appendJavaCode(StringBuilder testClassBuilder, TaskInfo taskInfo) {
        testClassBuilder.append("public class TestCase {\n");
        List<TaskTestCaseInfo> testCases = taskInfo.getTaskTestCases();
        for (int i = 0; i < testCases.size(); i++) {
            TaskTestCaseInfo testCase = testCases.get(i);
            testClassBuilder.append("public void testCase" + (i + 1) + "(){\n");
            testClassBuilder.append("Submission submission = new Submission();\n");
            TaskParameterInfo taskParameterInfo = taskInfo.getTaskParameters().get(0);
            String outputType = taskParameterInfo.getOutputParameters();
            if (outputType.equalsIgnoreCase("String")) {
                testClassBuilder.append("String result = submission." + taskParameterInfo.getMethodName() + "(");
            } else if (outputType.equalsIgnoreCase("Integer")) {
                testClassBuilder.append("int result = submission." + taskParameterInfo.getMethodName() + "(");
            }
            testClassBuilder.append(testCase.getInput());
            testClassBuilder.append(");\n");
            if (outputType.equalsIgnoreCase("String")) {
                testClassBuilder.append("if (!result.equals(\"" + testCase.getOutput() + "\")) System.out.println(\"Failed test case " + (i + 1) + "\");\n");
            } else if (outputType.equalsIgnoreCase("Integer")) {
                testClassBuilder.append("if (result != " + testCase.getOutput() + ") System.out.println(\"Failed test case " + (i + 1) + "\");\n");
            }
            testClassBuilder.append("else System.out.println(\"Passed test case " + (i + 1) + "\");\n");
            testClassBuilder.append("}\n");
        }
        testClassBuilder.append("public static void main(String[] args) {\n");
        testClassBuilder.append("TestCase testCase = new TestCase();\n");
        for (int i = 0; i < testCases.size(); i++) {
            testClassBuilder.append("testCase.testCase" + (i + 1) + "();\n");
        }
        testClassBuilder.append("}\n");
        testClassBuilder.append("}");
    }

    private void appendPythonCode(StringBuilder testClassBuilder, TaskInfo taskInfo) {
        testClassBuilder.append(pythonClassDeclaration);
        List<TaskTestCaseInfo> testCases = taskInfo.getTaskTestCases();
        for (int i = 0; i < testCases.size(); i++) {
            TaskTestCaseInfo testCase = testCases.get(i);
            Map<String, String> substitutionsMap = new HashMap();
            substitutionsMap.put("case_number", String.valueOf(i + 1));
            substitutionsMap.put("submission_method_name", taskInfo.getTaskParameters().get(0).getMethodName());
            substitutionsMap.put("input_arguments", testCases.get(i).getInput());
            String correctResult = testCases.get(i).getOutput();
            if (taskInfo.getTaskParameters().get(0).getOutputParameters().equalsIgnoreCase("String")) {
                correctResult = "'" + correctResult + "'";
            }
            substitutionsMap.put("correct_result", correctResult);
            testClassBuilder.append(StrSubstitutor.replace(pythonTestCase, substitutionsMap));
        }
        testClassBuilder.append(pythonRunTestCases);
        for (int i = 0; i < testCases.size(); i++) {
            testClassBuilder.append("    testCase.test_case_" + (i + 1) + "()\n");
        }
        testClassBuilder.append("\n");
    }

    private final String pythonClassDeclaration = "" +
            "from app.submission import Submission\n\n\n" +
            "class TestCase:\n" +
            "\n";

    private final String pythonTestCase = "" +
            "    def test_case_${case_number}(self):\n" +
            "        submission = Submission()\n" +
            "        result = submission.${submission_method_name}(${input_arguments})\n" +
            "        if result == ${correct_result}:\n" +
            "            print('Passed test case ${case_number}')\n" +
            "        else:\n" +
            "            print('Failed test case ${case_number}')\n\n";

    private final String pythonRunTestCases = "\n" +
            "if __name__ == \"__main__\":\n" +
            "    testCase = TestCase()\n";
}
