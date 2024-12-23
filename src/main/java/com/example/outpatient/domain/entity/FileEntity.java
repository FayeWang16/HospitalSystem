package com.example.outpatient.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "File Information")
public class FileEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "File Path")
    private String filePath;

    @Schema(description = "File Name")
    private String fileName;

    @Schema(description = "File Type")
    private String fileType;

    @Schema(description = "Original File Name")
    private String originalFileName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "Upload Time")
    private LocalDateTime uploadTime;

    public static FileEntity of(String filePath, String fileName, String fileType, String originalFileName, LocalDateTime uploadTime) {
        return new FileEntity(filePath, fileName, fileType, originalFileName, uploadTime);
    }
}