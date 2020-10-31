package com.virtuallab.task.usecase;

import com.virtuallab.common.Language;
import com.virtuallab.events.RunSubmissionEvent;
import com.virtuallab.events.TestRunnerEvent;
import com.virtuallab.task.dataprovider.*;
import com.virtuallab.task.entrypoint.CreateTaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreateTask {

    private final TaskRepository taskRepository;
    private final TaskParameterRepository taskParameterRepository;
    private final TaskTestCaseRepository taskTestCaseRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public CreateTask(TaskRepository taskRepository, TaskParameterRepository taskParameterRepository, TaskTestCaseRepository taskTestCaseRepository,
                      ApplicationEventPublisher eventPublisher) {
        this.taskRepository = taskRepository;
        this.taskParameterRepository = taskParameterRepository;
        this.taskTestCaseRepository = taskTestCaseRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public TaskEntity execute(CreateTaskRequest request) {
        // validation
        // check constraints

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(request.getTitle());
        taskEntity.setDescription(request.getDescription());
        taskEntity.setCreatedAt(LocalDateTime.now());
        // TODO get from context
        taskEntity.setCreatedBy("user-1");
        taskRepository.save(taskEntity);

        // TODO save raw parameters, because they are independent of the language and mapping will be done inside application
        List<TaskParameterEntity> taskParameterEntities = Arrays.stream(Language.values()).map(language -> {
            TaskParameterEntity taskParameterEntity = new TaskParameterEntity();
            taskParameterEntity.setTaskId(taskEntity.getId());
            taskParameterEntity.setLanguage(language.name());
            taskParameterEntity.setMethodName(request.getMethodName());
            List<InputParameterEntity> inputParametersEntity = request.getInputParameters().stream()
                .map(inputParameter -> new InputParameterEntity(inputParameter.getType(), inputParameter.getName()))
                .collect(Collectors.toList());
            taskParameterEntity.setInputParameters(inputParametersEntity);
            taskParameterEntity.setOutputParameters(request.getOutputParameters());
            return taskParameterEntity;
        }).collect(Collectors.toList());
        taskParameterRepository.saveAll(taskParameterEntities);

        List<TaskTestCaseEntity> taskTestCaseEntities = request.getTestCases().stream().map(testCase -> {
            TaskTestCaseEntity taskTestCaseEntity = new TaskTestCaseEntity();
            taskTestCaseEntity.setTaskId(taskEntity.getId());
            taskTestCaseEntity.setInput(testCase.getInputData());
            taskTestCaseEntity.setOutput(testCase.getExpectedData());
            return taskTestCaseEntity;
        }).collect(Collectors.toList());
        taskTestCaseRepository.saveAll(taskTestCaseEntities);

        eventPublisher.publishEvent(new TestRunnerEvent(taskEntity.getId(), Language.JAVA.name()));
        return taskEntity;
    }

}
