package com.virtuallab.util.security;

import com.virtuallab.user.UserEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static String getCurrentUserId() {
        SecurityContext context = SecurityContextHolder.getContext();
        UserEntity userEntity = (UserEntity) context.getAuthentication().getPrincipal();
        return userEntity.getId();
    }

}
