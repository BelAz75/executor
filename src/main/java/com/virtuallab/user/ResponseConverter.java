package com.virtuallab.user;

public class ResponseConverter {

    public static User toSearchResponse(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getEmail(), userEntity.getUsername());
    }
}
