package com.isaqurbanov.file_storage_service.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface IStorageProvider {
    void upload(MultipartFile file, String objectName);

    InputStream download(String key);

//    void delete(String key);
}
