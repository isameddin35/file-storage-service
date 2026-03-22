package com.isaqurbanov.file_storage_service.model.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "files")
@Data
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private String contentType;

    private Long size;

    private LocalDateTime uploadedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Provider provider;

    private String originalName;

    private boolean isDeleted = false;

    private LocalDateTime deletedAt;

}
