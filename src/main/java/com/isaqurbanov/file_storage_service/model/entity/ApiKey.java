package com.isaqurbanov.file_storage_service.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "api_keys")
@Data
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String hashedKey;

    private LocalDateTime createdAt = LocalDateTime.now();

    private boolean active = true;
}