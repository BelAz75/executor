package com.virtuallab.task.entrypoint;

import com.virtuallab.task.dataprovider.TaskEntity;
import com.virtuallab.task.usecase.TaskAssignment;
import com.virtuallab.task.usecase.TaskInfo;

import java.util.List;
import java.util.stream.Collectors;

public class ResponseConverter {

    public static TaskSearchResponse toSearchResponse(TaskEntity taskEntity) {
        return new TaskSearchResponse(taskEntity.getId(), taskEntity.getTitle(), taskEntity.getCreatedAt());
    }

    public static TaskResponse toResponse(TaskEntity taskEntity) {
        return new TaskResponse(taskEntity.getId());
    }

    public static TaskInfoResponse toInfoResponse(TaskInfo taskInfo) {
        TaskInfoResponse taskInfoResponse = new TaskInfoResponse(taskInfo.getId(), taskInfo.getTitle(), taskInfo.getDescription(), taskInfo.getCreatedAt());
        List<TaskParameterInfoResponse> taskParameters = taskInfo.getTaskParameters().stream().map(taskParameter ->
            new TaskParameterInfoResponse(
                taskParameter.getMethodName(),
                taskParameter.getLanguage(),
                taskParameter.getInputParameters().stream()
                    .map(inputParameter ->
                        new InputParameterInfoResponse(inputParameter.getType(), inputParameter.getName())
                    ).collect(Collectors.toList()),
                taskParameter.getOutputParameters()
            )
        ).collect(Collectors.toList());
        taskInfoResponse.setTaskParameters(taskParameters);

        // Test cases
        List<TaskTestCaseInfoResponse> taskTestCases = taskInfo.getTaskTestCases().stream().map(taskTestCase ->
            new TaskTestCaseInfoResponse(taskTestCase.getInput(), taskTestCase.getOutput())
        ).collect(Collectors.toList());
        taskInfoResponse.setTaskTestCases(taskTestCases);

        return taskInfoResponse;
    }

    public static TaskAssignmentResponse toResponse(TaskAssignment taskAssignment) {
        List<UserAssignmentResponse> userAssignmentsResponse = taskAssignment.getUsers().stream()
            .map(userAssignment -> new UserAssignmentResponse(userAssignment.getId()))
            .collect(Collectors.toList());

        List<GroupAssignmentResponse> groupAssignmentsResponse = taskAssignment.getGroups().stream()
            .map(groupAssignment -> new GroupAssignmentResponse(groupAssignment.getId()))
            .collect(Collectors.toList());

        return new TaskAssignmentResponse(taskAssignment.getDeadline(), userAssignmentsResponse, groupAssignmentsResponse);
    }

}
