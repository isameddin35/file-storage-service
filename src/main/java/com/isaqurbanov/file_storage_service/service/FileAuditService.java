package com.isaqurbanov.file_storage_service.service;

import com.isaqurbanov.file_storage_service.holder.CurrentUserHolder;
import com.isaqurbanov.file_storage_service.model.entity.ApiKey;
import com.isaqurbanov.file_storage_service.model.entity.AuditLog;
import com.isaqurbanov.file_storage_service.model.entity.FileMetadata;
import com.isaqurbanov.file_storage_service.model.entity.enumeration.Action;
import com.isaqurbanov.file_storage_service.model.entity.enumeration.EntityType;
import com.isaqurbanov.file_storage_service.repository.AuditLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileAuditService {

    private final AuditLogRepository auditLogRepository;
    private final CurrentUserHolder currentUserHolder;

    @Transactional
    public void logFileUpload(FileMetadata fileMetadata) {
        AuditLog auditLog = AuditLog.builder()
                .action(Action.UPLOAD)
                .entityType(EntityType.FILE)
                .entityId(fileMetadata.getId())
                .performedBy(currentUserHolder.getUser().getUsername())
                .details(
                        "File original name: " + fileMetadata.getOriginalName() +
                                " file size: " + fileMetadata.getSize())
                .build();

        auditLogRepository.save(auditLog);
    }

    public void logFileDownload(FileMetadata fileMetadata) {
        AuditLog auditLog = AuditLog.builder()
                .action(Action.DOWNLOAD)
                .entityType(EntityType.FILE)
                .entityId(fileMetadata.getId())
                .performedBy(currentUserHolder.getUser().getUsername())
                .details(
                        "File original name: " + fileMetadata.getOriginalName() +
                                " file size: " + fileMetadata.getSize()
                ).build();

        auditLogRepository.save(auditLog);
    }

    public void logFileDelete(FileMetadata fileMetadata) {
        AuditLog auditLog = AuditLog.builder()
                .action(Action.DELETE)
                .entityType(EntityType.FILE)
                .entityId(fileMetadata.getId())
                .performedBy(currentUserHolder.getUser().getUsername())
                .details(
                        "File original name: " + fileMetadata.getOriginalName() +
                                " file size: " + fileMetadata.getSize()
                ).build();

        auditLogRepository.save(auditLog);
    }

    public void logApiKeyGenerate(ApiKey generatedApiKey) {
        AuditLog auditLog = AuditLog.builder()
                .action(Action.API_KEY_GENERATE)
                .entityType(EntityType.API_KEY)
                .entityId(generatedApiKey.getId())
                .performedBy(currentUserHolder.getUser().getUsername())
                .build();

        auditLogRepository.save(auditLog);
    }
}
