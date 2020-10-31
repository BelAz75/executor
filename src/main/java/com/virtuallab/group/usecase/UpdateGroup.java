package com.virtuallab.group.usecase;

import com.virtuallab.group.dataprovider.GroupEntity;
import com.virtuallab.group.dataprovider.GroupEntityRepository;
import com.virtuallab.group.dataprovider.GroupMembershipEntity;
import com.virtuallab.group.entrypoint.GroupResponse;
import com.virtuallab.group.entrypoint.ResponseConverter;
import com.virtuallab.group.entrypoint.UpdateGroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UpdateGroup {

    private GroupEntityRepository groupEntityRepository;

    @Autowired
    public UpdateGroup(GroupEntityRepository groupEntityRepository) {
        this.groupEntityRepository = groupEntityRepository;
    }

    @Transactional
    public GroupResponse execute(String groupId, UpdateGroupRequest request) {
        GroupEntity groupEntity = groupEntityRepository.findById(groupId).orElseThrow(RuntimeException::new);
        groupEntity.setName(request.getName());
        Set<GroupMembershipEntity> memberships = request.getUserIds().stream().map(userId -> {
            GroupMembershipEntity groupMembershipEntity = new GroupMembershipEntity();
            groupMembershipEntity.setUserId(userId);
            return groupMembershipEntity;
        }).collect(Collectors.toSet());
        groupEntity.updateMemberships(memberships);
        return ResponseConverter.toResponse(groupEntityRepository.save(groupEntity));
    }

}
