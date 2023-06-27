//package com.teamjo.techeermarket.global.s3;
//
//
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/upload")
//@RequiredArgsConstructor
//public class TestUploadController {
//
//    private final AmazonS3Client amazonS3Client;
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucketName;
//
//    @PostMapping
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        try {
//            String fileName = file.getOriginalFilename();
//            String fileUrl= "https://" + bucketName +fileName;
//
////            String folderName = "thumbnails/";
//            String filePath =  file.getOriginalFilename();
//
//            ObjectMetadata metadata= new ObjectMetadata();     // 파일의 사이즈를 알려준다.
//            metadata.setContentType(file.getContentType());
//            metadata.setContentLength(file.getSize());
//
//            amazonS3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);   // 파일 업로드
//
//
//            return ResponseEntity.ok( amazonS3Client.getUrl(bucketName, filePath).toString());  // url 출력
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//}