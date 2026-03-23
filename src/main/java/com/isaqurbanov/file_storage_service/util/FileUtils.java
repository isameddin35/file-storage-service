package com.isaqurbanov.file_storage_service.util;

import java.util.UUID;

public class FileUtils {

    public static String generateUniqueObjectName(String filename) {
        return UUID.randomUUID() + "_" + filename;
    }
}
