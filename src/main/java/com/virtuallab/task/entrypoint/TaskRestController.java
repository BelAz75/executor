package com.virtuallab.task.entrypoint;

import com.virtuallab.util.rest.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskRestController {

    private final TaskRestService taskRestService;

    @Autowired
    public TaskRestController(TaskRestService taskRestService) {
        this.taskRestService = taskRestService;
    }

    @GetMapping
    public PageResponse<TaskSearchResponse> findTasks(
        @RequestParam(value = "pageNumber", defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int pageSize
    ) {
        return taskRestService.findTasks(page, pageSize);
    }

    @PostMapping
    public TaskResponse createTask(@RequestBody CreateTaskRequest createTaskRequest) {
        return taskRestService.createTask(createTaskRequest);
    }

    @PutMapping("/{taskId}")
    public TaskResponse updateTask(
        @PathVariable("taskId") String taskId,
        @RequestBody UpdateTaskRequest updateTaskRequest
    ) {
        return taskRestService.updateTask(taskId, updateTaskRequest);
    }

    @GetMapping("/{taskId}")
    public TaskInfoResponse getTask(
        @PathVariable("taskId") String taskId
    ) {
        return taskRestService.getTaskInfo(taskId);
    }

    @GetMapping("/assigned")
    public PageResponse<TaskSearchResponse> findAssignedTasks(
        @RequestParam(value = "pageNumber", defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int pageSize
    ) {
        return taskRestService.findAssignedTasks(page, pageSize);
    }

}
