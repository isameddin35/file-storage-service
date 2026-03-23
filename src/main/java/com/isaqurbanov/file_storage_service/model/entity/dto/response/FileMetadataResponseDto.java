package com.isaqurbanov.file_storage_service.model.entity.dto.response;

import com.isaqurbanov.file_storage_service.model.entity.Provider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileMetadataResponseDto {

    private Long id;

    private String filename;

    private String contentType;

    private Long size;

    private Provider provider;

    private String originalName;


}
