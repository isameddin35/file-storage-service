package com.isaqurbanov.file_storage_service.exception;

public class FileUploadException extends FileStorageException{
    public FileUploadException(String fileName) {
        super("Failed to upload file: " + fileName);
    }
}
