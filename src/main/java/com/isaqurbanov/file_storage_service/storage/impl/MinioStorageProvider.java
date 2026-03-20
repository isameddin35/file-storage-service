package com.isaqurbanov.file_storage_service.storage.impl;

import com.isaqurbanov.file_storage_service.exception.StorageException;
import com.isaqurbanov.file_storage_service.storage.IStorageProvider;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;


@RequiredArgsConstructor
public class MinioStorageProvider implements IStorageProvider {

    private final MinioClient minioClient;
    private final String bucketName;


    @Override
    public String upload(MultipartFile file) {

        try {
            String objectName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            return objectName;
        } catch (Exception e) {
            throw new StorageException("Failed to upload file", e);
        }
    }

    @Override
    public InputStream download(String objectName)  {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            throw new StorageException("Failed to download file", e);
        }
    }

    @Override
    public void delete(String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new StorageException("Failed to delete file", e);
        }
    }
}
