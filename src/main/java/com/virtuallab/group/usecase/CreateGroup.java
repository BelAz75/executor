package com.virtuallab.group.usecase;

import com.virtuallab.group.dataprovider.GroupEntity;
import com.virtuallab.group.dataprovider.GroupEntityRepository;
import com.virtuallab.group.entrypoint.CreateGroupRequest;
import com.virtuallab.group.entrypoint.GroupResponse;
import com.virtuallab.group.entrypoint.ResponseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateGroup {

    private GroupEntityRepository groupEntityRepository;

    @Autowired
    public CreateGroup(GroupEntityRepository groupEntityRepository) {
        this.groupEntityRepository = groupEntityRepository;
    }

    @Transactional
    public GroupResponse execute(CreateGroupRequest request) {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setName(request.getName());
        return ResponseConverter.toResponse(groupEntityRepository.save(groupEntity));
    }

}
