package com.isaqurbanov.file_storage_service.service;

import com.isaqurbanov.file_storage_service.exception.FileNotFoundException;
import com.isaqurbanov.file_storage_service.exception.FileStorageException;
import com.isaqurbanov.file_storage_service.exception.FileUploadException;
import com.isaqurbanov.file_storage_service.mapper.FileMetadataMapper;
import com.isaqurbanov.file_storage_service.model.entity.FileMetadata;
import com.isaqurbanov.file_storage_service.model.entity.Provider;
import com.isaqurbanov.file_storage_service.model.entity.dto.response.FileMetadataResponseDto;
import com.isaqurbanov.file_storage_service.repository.FileMetadataRepository;
import com.isaqurbanov.file_storage_service.storage.IStorageProvider;
import com.isaqurbanov.file_storage_service.util.FileUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.FetchNotFoundException;
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
            throw new FileUploadException(objectName);
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
                .orElseThrow(() -> new FileNotFoundException(objectName));

        if (fileMetadata.isDeleted()) {
            throw new FileNotFoundException(objectName);
        }

        fileAuditService.logFileDownload(fileMetadata);

        return storageProvider.download(fileMetadata.getFilename());

    }

    @Transactional
    public String delete(String objectName) {

        FileMetadata fileMetadata = fileMetadataRepository.findByFilename(objectName)
                .orElseThrow(() -> new FileNotFoundException(objectName));

        fileMetadata.setDeleted(true);
        fileMetadata.setDeletedAt(LocalDateTime.now());

        fileMetadataRepository.save(fileMetadata);

        fileAuditService.logFileDelete(fileMetadata);

        return "File " + objectName + " has been deleted";
    }
}
