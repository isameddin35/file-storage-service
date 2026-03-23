package com.isaqurbanov.file_storage_service.controller;

import com.isaqurbanov.file_storage_service.model.entity.dto.response.FileMetadataResponseDto;
import com.isaqurbanov.file_storage_service.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "ApiKeyAuth")
public class FileController {

    private final FileService fileService;

    @Operation(summary = "Upload a file", description = "Uploads a file and returns its metadata")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponse(
            responseCode = "200",
            description = "Upload a file",
            content = @Content(schema = @Schema(implementation = FileMetadataResponseDto.class))
    )
    public ResponseEntity<FileMetadataResponseDto> upload(
            @Parameter(description = "File to upload", required = true)
            @RequestParam("file") MultipartFile file
    ) {
        return ResponseEntity.ok().body(fileService.upload(file));
    }

    @Operation(summary = "Download a file", description = "Downloads a file by its name")
    @GetMapping
    public ResponseEntity<InputStreamResource> download(@RequestParam String objectName) {
        InputStream stream = fileService.download(objectName);

        String contentType = URLConnection.guessContentTypeFromName(objectName);
        MediaType mediaType = MediaType.parseMediaType(contentType);

        return ResponseEntity.ok().contentType(mediaType).body(new InputStreamResource(stream));
    }

    @Operation(summary = "Delete a file", description = "Deletes a file by its name")
    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam String objectName) {
        return ResponseEntity.ok(fileService.delete(objectName));
    }
}
