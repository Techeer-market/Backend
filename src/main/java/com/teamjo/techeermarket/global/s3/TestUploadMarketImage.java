//package com.teamjo.techeermarket.global.s3;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
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
//public class TestUploadMarketImage {
//
//
//    private final S3MarketService s3MarketService;
//
//    @Autowired
//    public TestUploadMarketImage(S3MarketService s3MarketService) {
//        this.s3MarketService = s3MarketService;
//    }
//
//    @PostMapping
//    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
//        String imageUrl = s3MarketService.uploadImage(file);
//        if (imageUrl != null) {
//            return ResponseEntity.status(HttpStatus.OK).body("Image uploaded successfully. Image URL: " + imageUrl);
//        } else {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
//        }
//    }
//
//}
