package com.virtuallab.task.entrypoint;

import com.virtuallab.task.dataprovider.TaskEntity;

public class ResponseConverter {

    public static TaskSearchResponse toSearchResponse(TaskEntity taskEntity) {
        return new TaskSearchResponse(taskEntity.getId(), taskEntity.getTitle(), taskEntity.getCreatedAt());
    }

    public static TaskResponse toResponse(TaskEntity taskEntity) {
        return new TaskResponse(taskEntity.getId());
    }

}
