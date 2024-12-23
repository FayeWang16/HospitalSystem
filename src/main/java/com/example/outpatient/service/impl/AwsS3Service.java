package com.example.outpatient.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class AwsS3Service {

    @Value("${aws.bucketName}")
    private String bucketName;

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.region}")
    private String region;

    /**
     * Upload a file to the specified S3 bucket.
     *
     * @param multipartFile The file to be uploaded.
     * @return The URL of the uploaded file.
     */
    public String upload(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new RuntimeException("The file is empty!");
        }
        try {
            // Prepare metadata for the uploaded file
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getSize());

            // Generate a unique key for the file
            String key = UUID.randomUUID().toString();

            // Check if the bucket exists, create it if it doesn't
            if (!amazonS3.doesBucketExistV2(bucketName)) {
                amazonS3.createBucket(bucketName);
            }

            // Prepare and upload the file to the S3 bucket
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, multipartFile.getInputStream(), objectMetadata);
            PutObjectResult putObjectResult = amazonS3.putObject(putObjectRequest);

            // Return the URL of the uploaded file
            return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, key);
        } catch (Exception e) {
            log.error("Failed to upload file to the bucket: {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}