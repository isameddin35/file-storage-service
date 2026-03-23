package com.isaqurbanov.file_storage_service.service;

import com.isaqurbanov.file_storage_service.exception.StorageException;
import com.isaqurbanov.file_storage_service.mapper.FileMetadataMapper;
import com.isaqurbanov.file_storage_service.model.entity.FileMetadata;
import com.isaqurbanov.file_storage_service.model.entity.Provider;
import com.isaqurbanov.file_storage_service.model.entity.dto.response.FileMetadataResponseDto;
import com.isaqurbanov.file_storage_service.repository.FileMetadataRepository;
import com.isaqurbanov.file_storage_service.storage.IStorageProvider;
import com.isaqurbanov.file_storage_service.util.FileUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FileService {
    private final IStorageProvider storageProvider;

    private final ProviderService providerService;
    private final FileMetadataRepository fileMetadataRepository;
    private final FileMetadataMapper mapper;
    private final FileAuditService fileAuditService;

    @Value("${storage.provider}")
    private String providerName;

    @Transactional
    public FileMetadataResponseDto upload(MultipartFile file) {

        Provider provider = providerService.getProvider(providerName);

        String objectName = FileUtils.generateUniqueObjectName(file.getOriginalFilename());

        try {
            storageProvider.upload(file, objectName);
        } catch (Exception e) {
            throw new StorageException("Failed to upload file to storage provider", e);
        }

        FileMetadata metadata = FileMetadata.builder()
                .filename(objectName)
                .originalName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .size(file.getSize())
                .provider(provider).build();

        fileMetadataRepository.save(metadata);

        fileAuditService.logFileUpload(metadata);

        return mapper.toDto(metadata);

    }

    public InputStream download(String objectName) {
        FileMetadata fileMetadata = fileMetadataRepository.findByFilename(objectName)
                .orElseThrow(() -> new RuntimeException("File not found"));

        if (fileMetadata.isDeleted()) {
            throw new RuntimeException("File has been deleted");
        }

        fileAuditService.logFileDownload(fileMetadata);

        return storageProvider.download(fileMetadata.getFilename());

    }

    @Transactional
    public void delete(String objectName) {

        FileMetadata fileMetadata = fileMetadataRepository.findByFilename(objectName)
                .orElseThrow(() -> new RuntimeException("File not found"));

        fileMetadata.setDeleted(true);
        fileMetadata.setDeletedAt(LocalDateTime.now());

        fileMetadataRepository.save(fileMetadata);

        fileAuditService.logFileDelete(fileMetadata);
    }
}
