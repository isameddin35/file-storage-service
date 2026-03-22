package com.isaqurbanov.file_storage_service.repository;

import com.isaqurbanov.file_storage_service.model.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    List<ApiKey> findAllByActiveTrue();

}
