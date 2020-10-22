package com.virtuallab.executor;

import java.util.UUID;

public class UUIDUtil {
    public static String generateShortUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0,5);
    }
}
