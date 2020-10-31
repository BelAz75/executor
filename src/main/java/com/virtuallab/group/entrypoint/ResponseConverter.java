package com.virtuallab.group.entrypoint;

import com.virtuallab.group.dataprovider.GroupEntity;

public class ResponseConverter {

    public static GroupSearchResponse toSearchResponse(GroupEntity groupEntity) {
        return new GroupSearchResponse(groupEntity.getId(), groupEntity.getName());
    }

    public static GroupResponse toResponse(GroupEntity groupEntity) {
        return new GroupResponse(groupEntity.getId(), groupEntity.getName());
    }

}
