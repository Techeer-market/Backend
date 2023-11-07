package com.teamjo.techeermarket.domain.images.service;

import com.teamjo.techeermarket.domain.images.entity.ProductImage;
import com.teamjo.techeermarket.domain.images.repository.ProductImageRepository;
import com.teamjo.techeermarket.global.S3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final S3Service s3Service;

    public ProductImage saveImage (ProductImage productImage) {
        productImageRepository.save(productImage);
        return productImage;
    }

}
