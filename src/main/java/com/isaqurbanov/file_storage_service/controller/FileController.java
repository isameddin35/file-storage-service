package com.isaqurbanov.file_storage_service.controller;

import com.isaqurbanov.file_storage_service.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam MultipartFile file) {
        return ResponseEntity.ok().body(fileService.upload(file));
    }

    @GetMapping
    public ResponseEntity<InputStreamResource> download(@RequestParam String objectName) {
        InputStream stream = fileService.download(objectName);
        return ResponseEntity.ok().body(new InputStreamResource(stream));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String objectName) {
        fileService.delete(objectName);
        return ResponseEntity.noContent().build();
    }
}
