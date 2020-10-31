package com.virtuallab;

import com.virtuallab.common.Language;
import com.virtuallab.submission.entrypoint.SubmissionRequest;
import com.virtuallab.submission.entrypoint.SubmissionService;
import com.virtuallab.task.dataprovider.TaskEntity;
import com.virtuallab.task.entrypoint.CreateTaskRequest;
import com.virtuallab.task.entrypoint.InputParameterRequest;
import com.virtuallab.task.entrypoint.TestCaseRequest;
import com.virtuallab.task.usecase.CreateTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class VirtualLabApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(VirtualLabApplication.class, args);
		CreateTask createTask = context.getBean(CreateTask.class);
		SubmissionService submissionService = context.getBean(SubmissionService.class);
		CreateTaskRequest createTaskRequest = new CreateTaskRequest();
		createTaskRequest.setTitle("Example");
		createTaskRequest.setDescription("Test Example");
		createTaskRequest.setMethodName("sum");
		List<InputParameterRequest> inputParameters = new ArrayList<>();

		InputParameterRequest firstParam = new InputParameterRequest();
		firstParam.setName("firstString");
		firstParam.setType("String");
		inputParameters.add(firstParam);

		InputParameterRequest secondParam = new InputParameterRequest();
		secondParam.setName("secondString");
		secondParam.setType("String");
		inputParameters.add(secondParam);
		createTaskRequest.setInputParameters(inputParameters);
		createTaskRequest.setOutputParameters("String");

		List<TestCaseRequest> testCases = new ArrayList<>();
		TestCaseRequest firstTestCase = new TestCaseRequest();
		firstTestCase.setInputData("\"foo\",\"bar\"");
		firstTestCase.setExpectedData("\"foobar\"");
		testCases.add(firstTestCase);
		createTaskRequest.setTestCases(testCases);
		TaskEntity taskEntity = createTask.execute("664370bc-db14-47f3-ae37-0d61af534631", createTaskRequest);

//		SubmissionRequest submissionRequest = new SubmissionRequest();
//		submissionRequest.setTaskId(taskEntity.getId());
//		submissionRequest.setLanguage(Language.JAVA.name().toLowerCase());
//		submissionRequest.setCode("public class Submission {\n" +
//				"\tpublic String sum(String firstString, String secondString) {\n" +
//				"\t\treturn firstString+secondString;\n" +
//				"\t}\n" +
//				"}");
//		submissionService.create(submissionRequest);
		System.out.println("Submission");
	}

}
