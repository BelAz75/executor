package com.virtuallab.task.usecase;

import com.virtuallab.group.dataprovider.GroupEntity;
import com.virtuallab.group.dataprovider.GroupEntityRepository;
import com.virtuallab.task.dataprovider.TaskEntity;
import com.virtuallab.task.dataprovider.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FindAssignedTasks {

    private TaskRepository taskRepository;
    private GroupEntityRepository groupEntityRepository;

    @Autowired
    public FindAssignedTasks(TaskRepository taskRepository, GroupEntityRepository groupEntityRepository) {
        this.taskRepository = taskRepository;
        this.groupEntityRepository = groupEntityRepository;
    }

    public Page<TaskEntity> execute(String userId, int page, int pageSize) {
        List<GroupEntity> groupsForUser = groupEntityRepository.findGroupsForUser(userId);
        Set<String> userGroupsIds = groupsForUser.stream().map(GroupEntity::getId).collect(Collectors.toSet());
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        if (userGroupsIds.isEmpty()) {
            return taskRepository.findAssignedTasksForUser(userId, pageRequest);
        } else {
            return taskRepository.findAssignedTasksForUserAndGroups(userId, userGroupsIds, pageRequest);
        }
    }

}
