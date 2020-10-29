package com.virtuallab.task.usecase;

import com.virtuallab.task.dataprovider.GroupTaskAssignmentEntity;
import com.virtuallab.task.dataprovider.TaskAssignmentEntity;
import com.virtuallab.task.dataprovider.TaskAssignmentRepository;
import com.virtuallab.task.dataprovider.UserTaskAssignmentEntity;
import com.virtuallab.task.entrypoint.AssignTaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssignTask {

    private final TaskAssignmentRepository taskAssignmentRepository;

    @Autowired
    public AssignTask(TaskAssignmentRepository taskAssignmentRepository) {
        this.taskAssignmentRepository = taskAssignmentRepository;
    }

    @Transactional
    public void execute(String taskId, AssignTaskRequest assignTaskRequest) {
        List<TaskAssignmentEntity> existingAssignments = taskAssignmentRepository.findByTaskId(taskId);
        // simple strategy - delete all existing assignments and create new ones
        if (!existingAssignments.isEmpty()) {
            taskAssignmentRepository.deleteAll(existingAssignments);
        }

        LocalDateTime deadline = assignTaskRequest.getDeadline();

        List<TaskAssignmentEntity> userTaskAssignmentEntities = assignTaskRequest.getUserIds().stream().map(userId -> {
            UserTaskAssignmentEntity userTaskAssignmentEntity = new UserTaskAssignmentEntity();
            userTaskAssignmentEntity.setUserId(userId);
            userTaskAssignmentEntity.setTaskId(taskId);
            userTaskAssignmentEntity.setDeadline(deadline);
            return userTaskAssignmentEntity;
        }).collect(Collectors.toList());

        List<TaskAssignmentEntity> groupTaskAssignmentEntities = assignTaskRequest.getGroupIds().stream().map(groupId -> {
            GroupTaskAssignmentEntity groupTaskAssignmentEntity = new GroupTaskAssignmentEntity();
            groupTaskAssignmentEntity.setGroupId(groupId);
            groupTaskAssignmentEntity.setTaskId(taskId);
            groupTaskAssignmentEntity.setDeadline(deadline);
            return groupTaskAssignmentEntity;
        }).collect(Collectors.toList());

        // overall assignments
        userTaskAssignmentEntities.addAll(groupTaskAssignmentEntities);

        taskAssignmentRepository.saveAll(userTaskAssignmentEntities);
    }

}
