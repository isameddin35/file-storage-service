package com.isaqurbanov.file_storage_service.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<String> handleStorageException(StorageException e) {
        return ResponseEntity.badRequest()
                .body("Storage exception: " + e);
    }
}
