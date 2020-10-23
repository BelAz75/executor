package com.virtuallab.task.entrypoint;

import com.virtuallab.task.dataprovider.TaskEntity;
import com.virtuallab.task.dataprovider.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class TaskTestController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/task/test")
    public List<TaskEntity> createAndFindTask() {
        TaskEntity task = new TaskEntity();
        task.setTitle("New task");
        task.setDescription("Description");
        task.setCreatedBy("uuid-1");
        task.setCreatedAt(LocalDateTime.now());
        taskRepository.saveAndFlush(task);

        return taskRepository.findAll();
    }

}
