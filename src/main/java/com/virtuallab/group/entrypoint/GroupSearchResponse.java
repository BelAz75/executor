package com.virtuallab.group.entrypoint;

import java.util.ArrayList;
import java.util.List;

public class GroupSearchResponse {
    private String id;
    private String name;
    private List<UserInGroupResponse> users = new ArrayList<>();

    public GroupSearchResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserInGroupResponse> getUsers() {
        return users;
    }

    public void setUsers(List<UserInGroupResponse> users) {
        this.users = users;
    }
}
