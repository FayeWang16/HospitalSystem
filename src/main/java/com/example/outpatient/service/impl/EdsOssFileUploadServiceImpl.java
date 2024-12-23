package com.example.outpatient.service.impl;

import com.example.outpatient.service.EdsOssFileUploadService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.CompletedFileUpload;
import software.amazon.awssdk.transfer.s3.model.UploadFileRequest;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class EdsOssFileUploadServiceImpl implements EdsOssFileUploadService {

    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.bucketName}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.endPoint}")
    private String endPoint;

    private S3Client s3Client;

    private S3AsyncClient s3AsyncClient;

    private S3Presigner presigner;

    private S3TransferManager transferManager;

    /**
     * Initializes the S3 client, presigner, and transfer manager.
     */
    @PostConstruct
    public void init() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        presigner = S3Presigner.builder()
                .region(Region.of(region))
                .endpointOverride(URI.create(endPoint))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
        s3Client = S3Client.builder()
                .region(Region.of(region)) // Region ID for East China (Zhejiang)
                .endpointOverride(URI.create(endPoint)) // Endpoint for East China (Zhejiang)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();

        s3AsyncClient = S3AsyncClient.builder()
                .region(Region.of(region)) // Region ID for East China (Zhejiang)
                .endpointOverride(URI.create(endPoint)) // Endpoint for East China (Zhejiang)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();

        transferManager = S3TransferManager.builder()
                .s3Client(s3AsyncClient)
                .build();
    }

    /**
     * High-level multipart upload using the TransferManager.
     *
     * @param keyName The key name of the file in S3.
     * @param file    The file to upload.
     * @return The URL of the uploaded file.
     */
    @Override
    public String highLevelMultipartUpload(String keyName, File file) {
        try {
            // Use TransferManager to upload the file
            UploadFileRequest uploadFileRequest = UploadFileRequest.builder()
                    .source(file.toPath())
                    .putObjectRequest(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(keyName)
                            .build())
                    .build();

            CompletableFuture<CompletedFileUpload> future = transferManager.uploadFile(uploadFileRequest).completionFuture();
            CompletedFileUpload completedUpload = future.join();

            // Get the file URL
            String url = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(keyName)).toString();
            log.info("File upload completed, URL: {}", url);
            return url;
        } catch (Exception e) {
            log.error("File upload error: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Simple upload method.
     *
     * @param keyName       The key name of the file in S3.
     * @param multipartFile The file to upload.
     * @return The URL of the uploaded file.
     */
    @Override
    public String simpleUploadToEDS(String keyName, MultipartFile multipartFile) {
        try {
            // Convert MultipartFile to Path
            Path tempFile = Files.createTempFile("upload-", multipartFile.getOriginalFilename());
            multipartFile.transferTo(tempFile.toFile());

            s3Client.putObject(b -> b.bucket(bucketName).key(keyName),
                    RequestBody.fromFile(tempFile.toFile()));
            // Delete the temporary file
            Files.delete(tempFile);
            return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, keyName);
        } catch (IOException e) {
            log.error("File upload failed: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}