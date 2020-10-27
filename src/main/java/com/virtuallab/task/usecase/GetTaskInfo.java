package com.virtuallab.task.usecase;

import com.virtuallab.task.dataprovider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GetTaskInfo {

    private final TaskRepository taskRepository;
    private final TaskParameterRepository taskParameterRepository;
    private final TaskTestCaseRepository taskTestCaseRepository;

    @Autowired
    public GetTaskInfo(TaskRepository taskRepository, TaskParameterRepository taskParameterRepository, TaskTestCaseRepository taskTestCaseRepository) {
        this.taskRepository = taskRepository;
        this.taskParameterRepository = taskParameterRepository;
        this.taskTestCaseRepository = taskTestCaseRepository;
    }

    public TaskInfo execute(String taskId) {
        // validation
        // check constraints
        Optional<TaskEntity> taskOptional = taskRepository.findById(taskId);
        if (!taskOptional.isPresent()) {
            // TODO exception handling
            throw new RuntimeException("Task not found");
        }
        TaskEntity taskEntity = taskOptional.get();

        TaskInfo taskInfo = new TaskInfo(taskEntity.getId(), taskEntity.getTitle(), taskEntity.getDescription(), taskEntity.getCreatedAt());

        // Parameters
        List<TaskParameterEntity> taskParameterEntities = taskParameterRepository.findByTaskId(taskId);
        List<TaskParameterInfo> taskParameters = taskParameterEntities.stream().map(taskParameterEntity ->
            new TaskParameterInfo(
                taskParameterEntity.getMethodName(),
                taskParameterEntity.getLanguage(),
                taskParameterEntity.getInputParameters().stream()
                    .map(inputParameterEntity ->
                        new InputParameterInfo(inputParameterEntity.getType(), inputParameterEntity.getName())
                    ).collect(Collectors.toList()),
                taskParameterEntity.getOutputParameters()
            )
        ).collect(Collectors.toList());
        taskInfo.setTaskParameters(taskParameters);

        // Test cases
        List<TaskTestCaseEntity> taskTestCaseEntities = taskTestCaseRepository.findByTaskId(taskId);
        List<TaskTestCaseInfo> taskTestCases = taskTestCaseEntities.stream().map(taskTestCaseEntity ->
            new TaskTestCaseInfo(taskTestCaseEntity.getInput(), taskTestCaseEntity.getOutput())
        ).collect(Collectors.toList());
        taskInfo.setTaskTestCases(taskTestCases);

        // TODO add raw parameter
        // TODO show generated code for all supported languages

        return taskInfo;
    }

}
