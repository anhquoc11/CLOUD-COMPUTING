package com.nguyentrananhquoc.library_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.region}")
    private String awsRegion;

    /**
     * Upload file ảnh lên AWS S3 và trả về Public URL của ảnh
     */
    public String uploadFile(MultipartFile file) throws IOException {
        // 1. Tạo tên file duy nhất để tránh bị ghi đè
        String originalFileName = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        String fileName = "covers/" + UUID.randomUUID().toString() + fileExtension;

        // 2. Tạo PutObjectRequest
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        // 3. Đẩy file lên S3
        s3Client.putObject(putObjectRequest, 
                RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        // 4. Trả về URL của file trên S3
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, awsRegion, fileName);
    }
}