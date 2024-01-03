package com.teamjo.techeermarket.domain.products.mapper;

import com.teamjo.techeermarket.domain.products.dto.request.ProductRequestDto;
import com.teamjo.techeermarket.domain.products.dto.request.ProductUpdateRequestDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductDetailViewDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductPreViewDto;
import com.teamjo.techeermarket.domain.products.entity.Categorys;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.images.entity.ProductImage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
                .location(productRequestDto.getLocation())
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
                .userId(product.getUsers().getId())
                .createdAt(product.getCreatedAt())
                .likes(product.getHeart())
                .views(product.getViews())
                .build();
    }



    // 상품 상세 불러오기
    public ProductDetailViewDto fromDetailEntity(Products product) {
        List<String> imageUrls = product.getProductImages().stream()
                .sorted(Comparator.comparingInt(ProductImage::getImageNum)) // imageNum으로 정렬
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList());

        return ProductDetailViewDto.builder()
                .productId(product.getId())
                .productImages(imageUrls)
                .name(product.getUsers().getName())
                .userId(product.getUsers().getId())
                .categoryName(product.getCategorys().getName())
                .title(product.getTitle())
                .content(product.getContent())
                .price(product.getPrice())
                .productState(product.getProductState())
                .location(product.getLocation())
                .likes(product.getHeart())
                .views(product.getViews())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .myheart(false)
                .build();
    }


    // 상품 업데이트
    public void updateProductFromDto (Products products, ProductRequestDto updateDto, Categorys categorys){
        products.setTitle(updateDto.getTitle());
        products.setContent(updateDto.getContent());
        products.setCategorys(categorys);
        products.setPrice(updateDto.getPrice());
        products.setLocation(updateDto.getLocation());
    }


}
