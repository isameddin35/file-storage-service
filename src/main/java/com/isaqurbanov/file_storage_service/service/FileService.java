package com.isaqurbanov.file_storage_service.service;

import com.isaqurbanov.file_storage_service.storage.IStorageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class FileService {
    private final IStorageProvider provider;

    public String upload(MultipartFile file) {
        return provider.upload(file);
    }

    public InputStream download(String objectName) {
        return provider.download(objectName);
    }

    public void delete(String objectName) {
        provider.delete(objectName);
    }

}
