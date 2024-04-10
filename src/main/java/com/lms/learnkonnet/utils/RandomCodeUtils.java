package com.lms.learnkonnet.utils;

import java.util.UUID;

public class RandomCodeUtils {
    public static String generateUniqueAccessKey() {
        UUID uuid = UUID.randomUUID();
        String uniqueAccessKey = uuid.toString();
        uniqueAccessKey = uniqueAccessKey.substring(0, 8);
        return uniqueAccessKey;
    }
}
