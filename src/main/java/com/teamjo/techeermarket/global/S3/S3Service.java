package com.teamjo.techeermarket.global.S3;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface S3Service {
    String uploadImage(BucketDir bucketDir, MultipartFile multipartFile) throws IOException;

    List<String> uploadProductImageList(BucketDir bucketDir, List<MultipartFile> imageFiles) throws IOException;

    void deleteImage(String imageUrl);

    String generateS3FileName(BucketDir bucketDir, String originalFilename);

    String getS3FileNameFromUrl(String imageUrl);

    // 이미지 리사이징
    List<String> uploadResizeProductImageList(BucketDir bucketDir, List<MultipartFile> imageFiles) throws IOException;

}
