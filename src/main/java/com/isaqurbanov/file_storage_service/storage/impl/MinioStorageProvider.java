package com.isaqurbanov.file_storage_service.storage.impl;

import com.isaqurbanov.file_storage_service.exception.StorageException;
import com.isaqurbanov.file_storage_service.storage.IStorageProvider;
import io.minio.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;


@RequiredArgsConstructor
public class MinioStorageProvider implements IStorageProvider {

    private final MinioClient minioClient;
    private final String bucket;

    @PostConstruct
    public void init() {
        initializeBucket();
    }

    public void initializeBucket() {
        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucket).build());

            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize bucket", e);
        }
    }


    @Override
    public void upload(MultipartFile file, String objectName) {

        try {

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

        } catch (Exception e) {
            throw new StorageException("Failed to upload file", e);
        }
    }

    @Override
    public InputStream download(String objectName) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
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
                    .bucket(bucket)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new StorageException("Failed to delete file", e);
        }
    }
}
