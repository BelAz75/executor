package com.virtuallab.group.entrypoint;

import com.virtuallab.util.rest.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
public class GroupRestController {

    private GroupRestService groupRestService;

    @Autowired
    public GroupRestController(GroupRestService groupRestService) {
        this.groupRestService = groupRestService;
    }

    @GetMapping
    public PageResponse<GroupSearchResponse> findGroups(
        @RequestParam(value = "pageNumber", defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int pageSize
    ) {
        return groupRestService.findGroups(page, pageSize);
    }

    @PostMapping
    public GroupResponse createGroup(
        @RequestBody CreateGroupRequest createGroupRequest
    ) {
        return groupRestService.createGroup(createGroupRequest);
    }

    @PutMapping("/{groupId}")
    public GroupResponse updateGroup(
        @PathVariable("groupId") String groupId,
        @RequestBody UpdateGroupRequest updateGroupRequest
    ) {
        return groupRestService.updateGroup(groupId, updateGroupRequest);
    }

}
