package com.teamjo.techeermarket.global.S3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final S3ServiceImpl s3ServiceImpl;

    @Transactional
    public String reUploadImage(BucketDir bucketDir, MultipartFile newImage, String oriImage) throws IOException {
        if (newImage == null || newImage.isEmpty()) {
            return oriImage;
        }
        s3ServiceImpl.deleteImage(oriImage);
        return s3ServiceImpl.uploadImage(bucketDir,newImage);
    }
}
