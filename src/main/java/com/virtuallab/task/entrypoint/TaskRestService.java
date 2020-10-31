package com.virtuallab.task.entrypoint;

import com.virtuallab.task.dataprovider.TaskEntity;
import com.virtuallab.task.usecase.*;
import com.virtuallab.util.rest.PageResponse;
import com.virtuallab.util.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskRestService {

    private final FindTasks findTasks;
    private final CreateTask createTask;
    private final UpdateTask updateTask;
    private final GetTaskInfo getTaskInfo;
    private final FindAssignedTasks findAssignedTasks;

    @Autowired
    public TaskRestService(FindTasks findTasks, CreateTask createTask, UpdateTask updateTask, GetTaskInfo getTaskInfo, FindAssignedTasks findAssignedTasks) {
        this.findTasks = findTasks;
        this.createTask = createTask;
        this.updateTask = updateTask;
        this.getTaskInfo = getTaskInfo;
        this.findAssignedTasks = findAssignedTasks;
    }

    public PageResponse<TaskSearchResponse> findTasks(int page, int pageSize) {
        String userId = SecurityUtils.getCurrentUserId();
        Page<TaskEntity> taskSearchResult = findTasks.execute(userId, page, pageSize);
        List<TaskSearchResponse> searchResponse = taskSearchResult.getContent().stream()
            .map(ResponseConverter::toSearchResponse)
            .collect(Collectors.toList());
        return new PageResponse<>(
            page,
            pageSize,
            taskSearchResult.getTotalPages(),
            taskSearchResult.getTotalElements(),
            searchResponse
        );
    }

    public TaskResponse createTask(CreateTaskRequest createTaskRequest) {
        String userId = SecurityUtils.getCurrentUserId();
        TaskEntity task = createTask.execute(userId, createTaskRequest);
        return ResponseConverter.toResponse(task);
    }

    public TaskResponse updateTask(String taskId, UpdateTaskRequest updateTaskRequest) {
        TaskEntity taskEntity = updateTask.execute(taskId, updateTaskRequest);
        return ResponseConverter.toResponse(taskEntity);
    }

    public TaskInfoResponse getTaskInfo(String taskId) {
        TaskInfo taskInfo = getTaskInfo.execute(taskId);
        return ResponseConverter.toInfoResponse(taskInfo);
    }

    public PageResponse<TaskSearchResponse> findAssignedTasks(int page, int pageSize) {
        String userId = SecurityUtils.getCurrentUserId();
        Page<TaskEntity> taskSearchResult = findAssignedTasks.execute(userId, page, pageSize);
        List<TaskSearchResponse> searchResponse = taskSearchResult.getContent().stream()
            .map(ResponseConverter::toSearchResponse)
            .collect(Collectors.toList());
        return new PageResponse<>(
            page,
            pageSize,
            taskSearchResult.getTotalPages(),
            taskSearchResult.getTotalElements(),
            searchResponse
        );
    }

}
