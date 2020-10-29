package com.virtuallab.task.entrypoint;

import com.virtuallab.task.usecase.AssignTask;
import com.virtuallab.task.usecase.FindAssignmentsForTask;
import com.virtuallab.task.usecase.TaskAssignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskAssignmentRestService {

    private final AssignTask assignTask;
    private final FindAssignmentsForTask findAssignmentsForTask;

    @Autowired
    public TaskAssignmentRestService(AssignTask assignTask, FindAssignmentsForTask findAssignmentsForTask) {
        this.assignTask = assignTask;
        this.findAssignmentsForTask = findAssignmentsForTask;
    }

    public TaskAssignmentResponse findAssignments(String taskId) {
        TaskAssignment taskAssignment = findAssignmentsForTask.execute(taskId);
        return ResponseConverter.toResponse(taskAssignment);
    }

    public void assignTask(String taskId, AssignTaskRequest assignTaskRequest) {
        assignTask.execute(taskId, assignTaskRequest);
    }

}
