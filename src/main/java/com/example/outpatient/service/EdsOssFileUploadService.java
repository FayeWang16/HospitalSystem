package com.example.outpatient.service;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;


public interface EdsOssFileUploadService {

    /**
     * High-level API for multipart upload of large files.
     * @param keyName Unique file identifier.
     * @param file The file to be uploaded.
     * @return The unique file identifier (keyName).
     * @throws InterruptedException If the upload is interrupted.
     */
    String highLevelMultipartUpload(String keyName, File file) throws InterruptedException;

    /**
     * Simple upload method.
     * @param keyName The unique file identifier.
     * @param multipartFile The file to be uploaded.
     * @return The URL or identifier of the uploaded file.
     */
    String simpleUploadToEDS(String keyName, MultipartFile multipartFile);
}