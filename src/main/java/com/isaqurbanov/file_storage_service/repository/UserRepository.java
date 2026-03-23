package com.isaqurbanov.file_storage_service.repository;

import com.isaqurbanov.file_storage_service.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
