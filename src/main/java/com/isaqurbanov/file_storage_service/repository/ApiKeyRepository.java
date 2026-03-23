package com.isaqurbanov.file_storage_service.repository;

import com.isaqurbanov.file_storage_service.model.entity.ApiKey;
import com.isaqurbanov.file_storage_service.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    List<ApiKey> findAllByActiveTrue();

    @Modifying
    @Query("UPDATE ApiKey k SET k.active = false WHERE k.user = :user AND k.active = true")
    void deactivateAllForUser(@Param("user") User user);

}
