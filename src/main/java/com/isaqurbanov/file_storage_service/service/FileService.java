package com.isaqurbanov.file_storage_service.service;

import com.isaqurbanov.file_storage_service.exception.StorageException;
import com.isaqurbanov.file_storage_service.model.entity.FileMetadata;
import com.isaqurbanov.file_storage_service.model.entity.Provider;
import com.isaqurbanov.file_storage_service.repository.FileMetadataRepository;
import com.isaqurbanov.file_storage_service.storage.IStorageProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final IStorageProvider storageProvider;

    private final ProviderService providerService;
    private final FileMetadataRepository fileMetadataRepository;

    @Value("${storage.provider}")
    private String providerName;

    @Transactional
    public FileMetadata upload(MultipartFile file) {

        Provider provider = providerService.getProvider(providerName);

        String objectName = generateUniqueObjectName(file.getOriginalFilename());

        try {
            storageProvider.upload(file, objectName);
        } catch (Exception e) {
            throw new StorageException("Failed to upload file to storage provider", e);
        }

        FileMetadata metadata = new FileMetadata();
        metadata.setFilename(objectName);
        metadata.setOriginalName(file.getOriginalFilename());
        metadata.setContentType(file.getContentType());
        metadata.setSize(file.getSize());
        metadata.setUploadedAt(LocalDateTime.now());
        metadata.setProvider(provider);

        try {
            return fileMetadataRepository.save(metadata);
        } catch (Exception e) {
            try {
                storageProvider.delete(objectName);
            } catch (Exception ex) {
                System.err.println("Failed to delete file after DB save failure: " + ex.getMessage());
            }
            throw new RuntimeException("Failed to save file metadata to DB", e);
        }
    }


    public InputStream download(String objectName) {
        FileMetadata file = fileMetadataRepository.findByFilename(objectName)
                .orElseThrow(() -> new RuntimeException("File not found"));

        if (file.isDeleted()) {
            throw new RuntimeException("File has been deleted");
        }

        return storageProvider.download(file.getFilename());

    }

//    @Transactional
//    public void delete(String objectName) {
//
//        FileMetadata file = fileMetadataRepository.findByFilename(objectName)
//                .orElseThrow(() -> new RuntimeException("File not found"));
//
//        try {
//            storageProvider.delete(objectName);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to delete file from storage", e);
//        }
//
//        fileMetadataRepository.delete(file);
//    }

    @Transactional
    public void delete(String objectName) {

        FileMetadata file = fileMetadataRepository.findByFilename(objectName)
                .orElseThrow(() -> new RuntimeException("File not found"));

        file.setDeleted(true);
        file.setDeletedAt(LocalDateTime.now());

        fileMetadataRepository.save(file);
    }

    private String generateUniqueObjectName(String filename) {
        return UUID.randomUUID() + "_" + filename;
    }

}
