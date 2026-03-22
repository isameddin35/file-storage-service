package com.isaqurbanov.file_storage_service.service;

import com.isaqurbanov.file_storage_service.model.entity.Provider;
import com.isaqurbanov.file_storage_service.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProviderService {

    private final ProviderRepository providerRepository;

    public Provider getProvider(String name) {
        return providerRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Provider not found: " + name));
    }

}
