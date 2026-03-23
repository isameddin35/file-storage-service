package com.isaqurbanov.file_storage_service.config;

import com.isaqurbanov.file_storage_service.storage.IStorageProvider;
import com.isaqurbanov.file_storage_service.storage.impl.MinioStorageProvider;
import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "storage")
public class StorageConfig {

    private String provider;

    private String bucket;

    @Bean
    @ConditionalOnProperty(name = "storage.provider", havingValue = "minio")
    public IStorageProvider minioProvider(MinioClient minioClient) {
        return new MinioStorageProvider(minioClient, bucket);
    }
}