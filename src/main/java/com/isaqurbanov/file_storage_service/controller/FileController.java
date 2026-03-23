package com.isaqurbanov.file_storage_service.controller;

import com.isaqurbanov.file_storage_service.model.entity.FileMetadata;
import com.isaqurbanov.file_storage_service.model.entity.dto.response.FileMetadataResponseDto;
import com.isaqurbanov.file_storage_service.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLConnection;

@RestController
@RequestMapping("file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("hello")
    public String hello() {
        return "HEllo";
    }

    @PostMapping
    public ResponseEntity<FileMetadataResponseDto> upload(@RequestParam MultipartFile file) {
        return ResponseEntity.ok().body(fileService.upload(file));
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> download(@RequestParam String objectName) {
        InputStream stream = fileService.download(objectName);

        String contentType = URLConnection.guessContentTypeFromName(objectName);
        MediaType mediaType = MediaType.parseMediaType(contentType);

        return ResponseEntity.ok().contentType(mediaType).body(new InputStreamResource(stream));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String objectName) {
        fileService.delete(objectName);
        return ResponseEntity.noContent().build();
    }
}
