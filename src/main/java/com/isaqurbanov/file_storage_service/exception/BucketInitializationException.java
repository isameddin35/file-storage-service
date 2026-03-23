package com.isaqurbanov.file_storage_service.exception;

public class BucketInitializationException extends FileStorageException{
    public BucketInitializationException(String bucketName) {
        super("Failed to initialize bucket: " + bucketName);
    }
}
