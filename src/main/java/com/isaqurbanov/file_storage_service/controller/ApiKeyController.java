package com.isaqurbanov.file_storage_service.controller;

import com.isaqurbanov.file_storage_service.service.ApiKeyService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Generate a new API key", description = "Generates a new API key for the authenticated developer")
    @PostMapping("api-keys")
    public ResponseEntity<String> generateApiKey() {
        return ResponseEntity.ok(apiKeyService.generateApiKey());
    }

}
