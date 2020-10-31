package com.virtuallab.task.usecase;

import com.virtuallab.task.dataprovider.TaskEntity;
import com.virtuallab.task.dataprovider.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class FindAssignedTasks {

    private TaskRepository taskRepository;

    @Autowired
    public FindAssignedTasks(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<TaskEntity> execute(String userId, int page, int pageSize) {
        return taskRepository.findAssignedTasks(userId, PageRequest.of(page - 1, pageSize));
    }

}
