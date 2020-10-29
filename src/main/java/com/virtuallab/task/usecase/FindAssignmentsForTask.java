package com.virtuallab.task.usecase;

import com.virtuallab.task.dataprovider.GroupTaskAssignmentEntity;
import com.virtuallab.task.dataprovider.TaskAssignmentEntity;
import com.virtuallab.task.dataprovider.TaskAssignmentRepository;
import com.virtuallab.task.dataprovider.UserTaskAssignmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindAssignmentsForTask {

    private final TaskAssignmentRepository taskAssignmentRepository;

    @Autowired
    public FindAssignmentsForTask(TaskAssignmentRepository taskAssignmentRepository) {
        this.taskAssignmentRepository = taskAssignmentRepository;
    }

    @Transactional
    public TaskAssignment execute(String taskId) {
        List<TaskAssignmentEntity> taskAssignmentEntities = taskAssignmentRepository.findByTaskId(taskId);

        if (taskAssignmentEntities.isEmpty()) {
            return new TaskAssignment();
        }

        // Take the first one - they have the same deadline
        LocalDateTime deadline = taskAssignmentEntities.get(0).getDeadline();

        List<UserTaskAssignmentEntity> userAssignments = taskAssignmentEntities.stream()
            .filter(t -> t instanceof UserTaskAssignmentEntity)
            .map(t -> (UserTaskAssignmentEntity) t)
            .collect(Collectors.toList());

        List<UserAssignment> userAssignmentsResult = userAssignments.stream()
            .map(userAssignment -> new UserAssignment(userAssignment.getUserId()))
            .collect(Collectors.toList());

        List<GroupTaskAssignmentEntity> groupAssignments = taskAssignmentEntities.stream()
            .filter(t -> t instanceof GroupTaskAssignmentEntity)
            .map(t -> (GroupTaskAssignmentEntity) t)
            .collect(Collectors.toList());

        List<GroupAssignment> groupAssignmentsResult = groupAssignments.stream()
            .map(groupAssignment -> new GroupAssignment(groupAssignment.getGroupId()))
            .collect(Collectors.toList());

        return new TaskAssignment(deadline, userAssignmentsResult, groupAssignmentsResult);
    }

}
