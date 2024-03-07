package com.teamjo.techeermarket.domain.images.service;

import com.teamjo.techeermarket.domain.images.entity.ProductImage;
import com.teamjo.techeermarket.domain.images.repository.ProductImageRepository;
import com.teamjo.techeermarket.global.S3.S3ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final S3ServiceImpl s3ServiceImpl;

    @Override
    public ProductImage saveImage(ProductImage productImage) {
        productImageRepository.save(productImage);
        return productImage;
    }

}
