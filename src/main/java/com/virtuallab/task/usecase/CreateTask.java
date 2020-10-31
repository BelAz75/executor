package com.virtuallab.task.usecase;

import com.virtuallab.common.Language;
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

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class CreateTask {

    private final TaskRepository taskRepository;
    private final TaskParameterRepository taskParameterRepository;
    private final TaskTestCaseRepository taskTestCaseRepository;

    // TODO remove
    private final TaskAssignmentRepository taskAssignmentRepository;

    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public CreateTask(TaskRepository taskRepository, TaskParameterRepository taskParameterRepository, TaskTestCaseRepository taskTestCaseRepository,
                      TaskAssignmentRepository taskAssignmentRepository, ApplicationEventPublisher eventPublisher) {
        this.taskRepository = taskRepository;
        this.taskParameterRepository = taskParameterRepository;
        this.taskTestCaseRepository = taskTestCaseRepository;
        this.taskAssignmentRepository = taskAssignmentRepository;
        this.eventPublisher = eventPublisher;
    }

    public TaskEntity execute(String userId, CreateTaskRequest request) {
        TaskEntity taskEntity = saveEntities(userId, request);
        eventPublisher.publishEvent(new TestRunnerEvent(taskEntity.getId(), Language.JAVA.name()));
        return taskEntity;
    }

    @Transactional
    public TaskEntity saveEntities(String userId, CreateTaskRequest request) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(request.getTitle());
        taskEntity.setDescription(request.getDescription());
        taskEntity.setCreatedAt(LocalDateTime.now());
        taskEntity.setCreatedBy(userId);
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

        // TODO fix me - assign task to user for demo
        UserTaskAssignmentEntity userTaskAssignmentEntity = new UserTaskAssignmentEntity();
        userTaskAssignmentEntity.setTaskId(taskEntity.getId());
        userTaskAssignmentEntity.setDeadline(LocalDateTime.now().plus(10, DAYS));
        userTaskAssignmentEntity.setUserId("664370bc-db14-47f3-ae37-0d61af534631");
        taskAssignmentRepository.save(userTaskAssignmentEntity);

        return taskEntity;
    }

}
