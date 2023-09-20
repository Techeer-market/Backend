//package com.teamjo.techeermarket.global.s3;
//
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.util.UUID;
//
//@Service
//@EnableAutoConfiguration
//public class S3MarketService {
//
//    private final AmazonS3Client s3Client; // AWS S3 클라이언트
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucketName;
//
//    @Autowired
//    public S3MarketService(AmazonS3Client s3Client) {
//        this.s3Client = s3Client;
//    }
//
//    public String uploadImage(MultipartFile file, String fileName) {
//        try {
//            // 파일의 사이즈를 알려줌
//            InputStream inputStream = new ByteArrayInputStream(file.getBytes());
//            ObjectMetadata metadata = new ObjectMetadata();
//            metadata.setContentLength(file.getSize());
//            metadata.setContentType(file.getContentType());
//
//            // 버킷 이름과 파일 경로 지정
////            String fileUUID = UUID.randomUUID().toString(); // 파일 이름 생성
//            String filePath = "markets/" + fileName ;
////            String folderName = "markets/"+fileUUID;
////            String filePath = folderName + file.getOriginalFilename();
//
//            // S3에 파일 업로드
//            PutObjectRequest request = new PutObjectRequest(bucketName, filePath, inputStream, metadata);
//            s3Client.putObject(request);
//
//            // 업로드한 파일의 URL 반환
//            return s3Client.getUrl(bucketName, filePath).toString();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
//
//
