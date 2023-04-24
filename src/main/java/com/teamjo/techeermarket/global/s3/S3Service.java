package com.teamjo.techeermarket.global.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
@EnableAutoConfiguration
public class S3Service {

    @Autowired
    private AmazonS3 s3Client; // AWS S3 클라이언트

    @Value("${cloud.aws.s3.bucket}")
    private String bucketNameYML;

    public String uploadImage(MultipartFile file) {
        try {
            InputStream inputStream = new ByteArrayInputStream(file.getBytes());
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            // 버킷 이름과 파일 경로 지정
            String fileUUID = UUID.randomUUID().toString(); // 파일 이름 생성
            String folderName = "thumbnails/"+fileUUID+"/";
            String filePath = folderName + file.getOriginalFilename();

            // S3에 파일 업로드
            PutObjectRequest request = new PutObjectRequest(bucketNameYML, filePath, inputStream, metadata);
            s3Client.putObject(request);

            // 업로드한 파일의 URL 반환
            return s3Client.getUrl(bucketNameYML, filePath).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
