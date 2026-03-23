package com.isaqurbanov.file_storage_service.mapper;

import com.isaqurbanov.file_storage_service.model.entity.FileMetadata;
import com.isaqurbanov.file_storage_service.model.entity.dto.response.FileMetadataResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMetadataMapper {

    FileMetadataResponseDto toDto(FileMetadata fileMetadata);
}
