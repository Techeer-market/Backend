package com.teamjo.techeermarket.domain.images.controller;

import com.teamjo.techeermarket.domain.images.dto.ImageDto;
import com.teamjo.techeermarket.global.S3.BucketDir;
import com.teamjo.techeermarket.global.S3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/test")
public class ProductImageController {

    private final S3Service s3Service;

    // 이미지 리스트로 s3업로드 테스트  --> 성공..!
    @PostMapping("/images")
    public List<String> uploadProductImages(@Validated @ModelAttribute ImageDto imageDto) throws IOException {
        List<MultipartFile> imageFiles = imageDto.getProductImages();
        List<String> imageUrls = s3Service.uploadProductImageList(BucketDir.PRODUCT, imageFiles);
        // 이미지 URL 리스트를 저장하거나 다른 작업 수행
        return imageUrls;
    }

}
