package com.virtuallab.task.usecase;

import com.virtuallab.common.Language;
import com.virtuallab.task.dataprovider.*;
import com.virtuallab.task.entrypoint.UpdateTaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UpdateTask {

    private final TaskRepository taskRepository;
    private final TaskParameterRepository taskParameterRepository;
    private final TaskTestCaseRepository taskTestCaseRepository;

    @Autowired
    public UpdateTask(TaskRepository taskRepository, TaskParameterRepository taskParameterRepository, TaskTestCaseRepository taskTestCaseRepository) {
        this.taskRepository = taskRepository;
        this.taskParameterRepository = taskParameterRepository;
        this.taskTestCaseRepository = taskTestCaseRepository;
    }

    @Transactional
    public TaskEntity execute(String taskId, UpdateTaskRequest request) {
        // validation
        // check constraints
        Optional<TaskEntity> optionalTask = taskRepository.findById(taskId);
        if (!optionalTask.isPresent()) {
            // TODO exception handling
            throw new RuntimeException("Task not found");
        }
        TaskEntity taskEntity = optionalTask.get();
        taskEntity.setTitle(request.getTitle());
        taskEntity.setDescription(request.getDescription());
        taskRepository.save(taskEntity);

        // simple strategy - remove existing and create new ones
        List<TaskParameterEntity> existingTaskParameters = taskParameterRepository.findByTaskId(taskId);
        if (!existingTaskParameters.isEmpty()) {
            taskParameterRepository.deleteInBatch(existingTaskParameters);
        }
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

        // simple strategy - remove existing and create new ones
        List<TaskTestCaseEntity> existingTaskTestCaseEntities = taskTestCaseRepository.findByTaskId(taskId);
        if (!existingTaskTestCaseEntities.isEmpty()) {
            taskTestCaseRepository.deleteInBatch(existingTaskTestCaseEntities);
        }
        List<TaskTestCaseEntity> taskTestCaseEntities = request.getTestCases().stream().map(testCase -> {
            TaskTestCaseEntity taskTestCaseEntity = new TaskTestCaseEntity();
            taskTestCaseEntity.setTaskId(taskEntity.getId());
            taskTestCaseEntity.setInput(testCase.getInputData());
            taskTestCaseEntity.setOutput(testCase.getExpectedData());
            return taskTestCaseEntity;
        }).collect(Collectors.toList());
        taskTestCaseRepository.saveAll(taskTestCaseEntities);

        return taskEntity;
    }

}
