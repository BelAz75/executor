package com.virtuallab.group.entrypoint;

import com.virtuallab.group.usecase.CreateGroup;
import com.virtuallab.group.usecase.FindGroups;
import com.virtuallab.group.usecase.UpdateGroup;
import com.virtuallab.util.rest.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupRestService {

    private FindGroups findGroups;
    private CreateGroup createGroup;
    private UpdateGroup updateGroup;

    @Autowired
    public GroupRestService(FindGroups findGroups, CreateGroup createGroup, UpdateGroup updateGroup) {
        this.findGroups = findGroups;
        this.createGroup = createGroup;
        this.updateGroup = updateGroup;
    }

    public PageResponse<GroupSearchResponse> findGroups(int page, int pageSize) {
        return findGroups.execute(page, pageSize);
    }

    public GroupResponse createGroup(CreateGroupRequest request) {
        return createGroup.execute(request);
    }

    public GroupResponse updateGroup(String groupId, UpdateGroupRequest request) {
        return updateGroup.execute(groupId, request);
    }

}
