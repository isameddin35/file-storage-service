package com.isaqurbanov.file_storage_service.controller;

import com.isaqurbanov.file_storage_service.model.entity.ApiKey;
import com.isaqurbanov.file_storage_service.repository.ApiKeyRepository;
import com.isaqurbanov.file_storage_service.util.ApiKeyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class ApiKeyController {

    private final ApiKeyRepository apiKeyRepository;

    @PostMapping("apikeys")
    public ResponseEntity<String> generateApiKey() {
        apiKeyRepository.findAllByActiveTrue()
                .forEach(k -> {
                    k.setActive(false);
                    apiKeyRepository.save(k);
                });

        String rawKey = ApiKeyUtils.generateKey();

        ApiKey apiKey = new ApiKey();
        apiKey.setHashedKey(ApiKeyUtils.hashKey(rawKey));
        apiKeyRepository.save(apiKey);

        return ResponseEntity.ok(rawKey);
    }

}
