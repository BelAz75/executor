package com.virtuallab.group.usecase;

import com.virtuallab.group.dataprovider.GroupEntity;
import com.virtuallab.group.dataprovider.GroupEntityRepository;
import com.virtuallab.group.dataprovider.GroupMembershipEntity;
import com.virtuallab.group.entrypoint.GroupSearchResponse;
import com.virtuallab.group.entrypoint.ResponseConverter;
import com.virtuallab.group.entrypoint.UserInGroupResponse;
import com.virtuallab.user.User;
import com.virtuallab.user.UserEntity;
import com.virtuallab.user.UserEntityRepository;
import com.virtuallab.util.rest.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindGroups {

    private GroupEntityRepository groupEntityRepository;
    private UserEntityRepository userEntityRepository;

    @Autowired
    public FindGroups(GroupEntityRepository groupEntityRepository, UserEntityRepository userEntityRepository) {
        this.groupEntityRepository = groupEntityRepository;
        this.userEntityRepository = userEntityRepository;
    }

    @Transactional(readOnly = true)
    public PageResponse<GroupSearchResponse> execute(int page, int pageSize) {
        Page<GroupEntity> groups = groupEntityRepository.findAll(PageRequest.of(page - 1, pageSize));

        List<GroupSearchResponse> searchResponse = groups.getContent().stream()
            .map(groupEntity -> {
                GroupSearchResponse groupSearchResponse = ResponseConverter.toSearchResponse(groupEntity);
                List<String> userIds = groupEntity.getMemberships().stream().map(GroupMembershipEntity::getUserId).collect(Collectors.toList());
                List<UserEntity> userEntities = userEntityRepository.findAllById(userIds);
                List<UserInGroupResponse> usersInGroup = userEntities.stream().map(userEntity -> new UserInGroupResponse(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName())).collect(Collectors.toList());
                groupSearchResponse.setUsers(usersInGroup);
                return groupSearchResponse;
            })
            .collect(Collectors.toList());

        return new PageResponse<>(
            page,
            pageSize,
            groups.getTotalPages(),
            groups.getTotalElements(),
            searchResponse
        );
    }

}
