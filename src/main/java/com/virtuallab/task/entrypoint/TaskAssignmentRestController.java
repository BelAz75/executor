package com.virtuallab.task.entrypoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task/{taskId}/assignment")
public class TaskAssignmentRestController {

    private final TaskAssignmentRestService taskAssignmentRestService;

    @Autowired
    public TaskAssignmentRestController(TaskAssignmentRestService taskAssignmentRestService) {
        this.taskAssignmentRestService = taskAssignmentRestService;
    }

    @GetMapping
    public TaskAssignmentResponse findAssignments(@PathVariable("taskId") String taskId) {
        return taskAssignmentRestService.findAssignments(taskId);
    }

    @PutMapping
    public void assignTask(
        @PathVariable("taskId") String taskId,
        @RequestBody AssignTaskRequest assignTaskRequest
    ) {
        taskAssignmentRestService.assignTask(taskId, assignTaskRequest);
    }

}
