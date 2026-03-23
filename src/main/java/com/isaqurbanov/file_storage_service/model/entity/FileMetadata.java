package com.isaqurbanov.file_storage_service.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "files")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private String contentType;

    private Long size;


    @ManyToOne(fetch = FetchType.LAZY)
    private Provider provider;

    private String originalName;

    private boolean isDeleted = false;

    private LocalDateTime deletedAt;

    private LocalDateTime uploadedAt;

    @PostPersist
    public void prePersist() {
        this.uploadedAt = LocalDateTime.now();
    }
}
