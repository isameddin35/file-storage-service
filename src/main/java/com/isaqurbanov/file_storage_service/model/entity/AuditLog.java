package com.isaqurbanov.file_storage_service.model.entity;

import com.isaqurbanov.file_storage_service.model.entity.enumeration.Action;
import com.isaqurbanov.file_storage_service.model.entity.enumeration.EntityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Action action;

    @Enumerated(EnumType.STRING)
    private EntityType entityType;

    private Long entityId;

    private String performedBy;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    private String details;
}
