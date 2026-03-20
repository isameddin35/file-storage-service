package com.isaqurbanov.file_storage_service.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface IStorageProvider {
    String upload(MultipartFile file);

    InputStream download(String key);

    void delete(String key);
}
