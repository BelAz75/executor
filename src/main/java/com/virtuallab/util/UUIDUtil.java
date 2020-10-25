package com.virtuallab.util;

import java.util.UUID;

public class UUIDUtil {

    public static String generateShortUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0,5);
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

}
