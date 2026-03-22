package com.isaqurbanov.file_storage_service.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

public class ApiKeyUtils {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hashKey(String rawKey) {
        return encoder.encode(rawKey);
    }

    public static boolean matches(String rawKey, String hashed) {
        return encoder.matches(rawKey, hashed);
    }

    public static String generateKey() {
        return UUID.randomUUID().toString();
    }
}