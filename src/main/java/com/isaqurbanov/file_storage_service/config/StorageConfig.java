package com.isaqurbanov.file_storage_service.config;

import com.isaqurbanov.file_storage_service.storage.IStorageProvider;
import com.isaqurbanov.file_storage_service.storage.impl.MinioStorageProvider;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ConfigurationProperties(prefix = "storage")
public class StorageConfig {

    @Value("${storage.provider}")
    private String provider;

    @Value("${storage.bucket-name}")
    private String bucketName;

    @Bean
    public IStorageProvider storageProvider(MinioClient minioClient) {

        if ("minio_local".equalsIgnoreCase(provider) ||
                "minio_prod".equalsIgnoreCase(provider)) {
            return new MinioStorageProvider(minioClient, bucketName);
        }

        throw new RuntimeException("Invalid storage provider: " + provider);
    }
}
