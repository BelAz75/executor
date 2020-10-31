package com.virtuallab.task.usecase;

import com.virtuallab.task.dataprovider.TaskEntity;
import com.virtuallab.task.dataprovider.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class FindTasks {

    private TaskRepository taskRepository;

    @Autowired
    public FindTasks(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<TaskEntity> execute(String userId, int page, int pageSize) {
        return taskRepository.findAllByCreatedBy(userId, PageRequest.of(page - 1, pageSize));
    }

}
