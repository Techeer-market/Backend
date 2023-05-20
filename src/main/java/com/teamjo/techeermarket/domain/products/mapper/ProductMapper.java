package com.teamjo.techeermarket.domain.products.mapper;


import com.teamjo.techeermarket.domain.category.entity.Categorys;
import com.teamjo.techeermarket.domain.products.dto.request.ProductRequestDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductResponseDto;
import com.teamjo.techeermarket.domain.products.entity.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    public Products toEntity(ProductRequestDto productRequestDto, Categorys categorys){
        return Products.builder()
//                .users(users)
                .title(productRequestDto.getTitle())
                .productUuid(UUID.randomUUID())
                .categorys(categorys)               // 카테고리!
                .description(productRequestDto.getDescription())
                .price(productRequestDto.getPrice())
                .productState(productRequestDto.getProductState())
                .build();
    }

    public ProductResponseDto fromEntity(Products products){
        return ProductResponseDto.builder()
                .id(products.getId())
                .productUuid(products.getProductUuid())
                .categoryUuid(products.getCategorys().getCategoryUuid())   //카테고리!
                .categoryName(products.getCategorys().getName())  // 카테고리 !
                .title(products.getTitle())
                .description(products.getDescription())
                .price(products.getPrice())
                .productState(products.getProductState())
                .views(products.getViews())
                .createdDate(products.getCreatedDate())
                .modifiedDate(products.getModifiedDate())
                .build();
    }

}
