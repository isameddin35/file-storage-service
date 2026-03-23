package com.isaqurbanov.file_storage_service.exception;

public class FileNotFoundException extends FileStorageException{
    public FileNotFoundException(String fileName) {
        super("File not found: " + fileName);
    }
}
