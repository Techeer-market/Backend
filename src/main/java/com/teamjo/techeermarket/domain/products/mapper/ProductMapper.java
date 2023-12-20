package com.teamjo.techeermarket.domain.products.mapper;

import com.teamjo.techeermarket.domain.products.dto.request.ProductRequestDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductPreViewDto;
import com.teamjo.techeermarket.domain.products.entity.Categorys;
import com.teamjo.techeermarket.domain.products.entity.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    // db에 상품 게시물 저장
    public Products saveToEntity(ProductRequestDto productRequestDto, Categorys categorysId) {
        return Products.builder()
                .title(productRequestDto.getTitle())
                .content(productRequestDto.getContent())
                .categorys(categorysId)
                .price(productRequestDto.getPrice())
                .build();
    }

    // 상품 목록 불러오기
    public ProductPreViewDto fromListEntity(Products product){
        return ProductPreViewDto.builder()
                .productId(product.getId())
                .title(product.getTitle())
                .thumbnailURL(product.getThumbnail())
                .name(product.getUsers().getName())
                .price(product.getPrice())
                .createdAt(product.getCreatedAt())
                .likes(product.getHeart())
                .views(product.getViews())
                .build();
    }

}
