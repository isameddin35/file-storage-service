package com.isaqurbanov.file_storage_service.service;

import com.isaqurbanov.file_storage_service.holder.CurrentUserHolder;
import com.isaqurbanov.file_storage_service.model.entity.ApiKey;
import com.isaqurbanov.file_storage_service.model.entity.User;
import com.isaqurbanov.file_storage_service.repository.ApiKeyRepository;
import com.isaqurbanov.file_storage_service.repository.UserRepository;
import com.isaqurbanov.file_storage_service.util.ApiKeyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
@RequiredArgsConstructor
public class ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;
    private final FileAuditService fileAuditService;
    private final UserRepository userRepository;
    private final CurrentUserHolder currentUserHolder;

    @Transactional
    public String generateApiKey() {

        User user = getOrCreateCurrentUser();

        apiKeyRepository.deactivateAllForUser(user);

        String rawKey = ApiKeyUtils.generateKey();
        ApiKey apiKey = new ApiKey();
        apiKey.setHashedKey(ApiKeyUtils.hashKey(rawKey));
        apiKey.setUser(user);
        user.getApiKeys().add(apiKey);
        apiKeyRepository.save(apiKey);

        fileAuditService.logApiKeyGenerate(apiKey);

        System.out.println("API key generated for user: " + user.getUsername());

        return rawKey;
    }

    private User getOrCreateCurrentUser() {
        if (currentUserHolder.isInitialized()) {
            User user = currentUserHolder.getUser();
            if (user.getId() == null) {
                return userRepository.save(user);
            }
            return user;
        }

        String devUsername = getDevUsername();
        User user = new User();
        user.setUsername(devUsername);
        currentUserHolder.setUser(user);
        return userRepository.save(user);
    }

    private String getDevUsername() {
        try {
            return System.getProperty("os.name") +
                    System.getProperty("user.name") +
                    InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            System.out.println("Host isn't known, using fallback username");
            return "unknown-host-user";
        }
    }
}
