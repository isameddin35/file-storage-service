package com.isaqurbanov.file_storage_service.controller;

import com.isaqurbanov.file_storage_service.service.ApiKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class ApiKeyController {

    private final ApiKeyService apiKeyService;

    @CrossOrigin(origins = "*")
    @PostMapping("api-keys")
    public ResponseEntity<String> generateApiKey() {
        return ResponseEntity.ok(apiKeyService.generateApiKey());
    }

}
