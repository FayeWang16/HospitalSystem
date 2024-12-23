package com.example.outpatient.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.example.outpatient.domain.ApiResult;
import com.example.outpatient.domain.entity.FileEntity;
import com.example.outpatient.service.EdsOssFileUploadService;
import com.example.outpatient.service.impl.AwsS3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Tag(name = "File API")
@RestController
public class FileController {

    @Autowired
    EdsOssFileUploadService edsOssFileUploadService;

    @Autowired
    AwsS3Service awsS3Service;

    @Operation(summary = "Upload a file")
    @PostMapping("/upload")
    public ApiResult<String> upload(@RequestBody MultipartFile file) throws IOException {
        // String url = edsOssFileUploadService.simpleUploadToEDS(file.getOriginalFilename(), file);
        String url = awsS3Service.upload(file);
        return ApiResult.ok(url);
    }
}