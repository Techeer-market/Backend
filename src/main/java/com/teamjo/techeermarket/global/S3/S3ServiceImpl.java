package com.teamjo.techeermarket.global.S3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.teamjo.techeermarket.global.exception.global.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class S3ServiceImpl implements S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    // 이미지 파일 업로드
    @Override
    public String uploadImage(BucketDir bucketDir, MultipartFile multipartFile) throws IOException {
        String s3FileName = generateS3FileName(bucketDir, multipartFile.getOriginalFilename());

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getSize());
        objMeta.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);
        return amazonS3.getUrl(bucket, s3FileName).toString();
    }


    // 이미지 리스트 형식 업로드
    @Override
    public List<String> uploadProductImageList(BucketDir bucketDir, List<MultipartFile> imageFiles) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile imageFile : imageFiles) {
            String imageUrl = uploadImage(bucketDir, imageFile);
            imageUrls.add(imageUrl);
        }
        return imageUrls;
    }



    // 이미지 삭제
    @Override
    public void deleteImage(String imageUrl) {
        String s3FileName = getS3FileNameFromUrl(imageUrl);
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, s3FileName);
        amazonS3.deleteObject(deleteObjectRequest);
    }


    // S3 파일 이름 생성
    @Override
    public String generateS3FileName(BucketDir bucketDir, String originalFilename) {
        return bucketDir.getDirName() + "/" + UUID.randomUUID() + "-" + originalFilename;
    }

    // 이미지 url로 파일 이름 가져오기
    @Override
    public String getS3FileNameFromUrl(String imageUrl) {
        final String delimiter = ".com/";
        int index = imageUrl.indexOf(delimiter);
        if (index == -1) { throw new ImageNotFoundException(); }

        String bucketAndKey = imageUrl.substring(index + delimiter.length());
        return URLDecoder.decode(bucketAndKey, StandardCharsets.UTF_8);
    }



}
