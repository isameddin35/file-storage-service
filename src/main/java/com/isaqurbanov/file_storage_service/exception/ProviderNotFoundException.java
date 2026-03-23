package com.isaqurbanov.file_storage_service.exception;

public class ProviderNotFoundException extends FileStorageException{
    public ProviderNotFoundException(String providerName) {
        super("Provider not found: " + providerName);
    }
}
