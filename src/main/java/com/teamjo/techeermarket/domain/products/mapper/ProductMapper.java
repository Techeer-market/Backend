package com.teamjo.techeermarket.domain.products.mapper;


import com.teamjo.techeermarket.domain.products.dto.request.ProductRequestDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductResponseDto;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.users.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    public Products toEntity(ProductRequestDto productRequestDto){
        return Products.builder()
//                .users(users)
                .title(productRequestDto.getTitle())
                .description(productRequestDto.getDescription())
                .price(productRequestDto.getPrice())
                .build();
    }

    public ProductResponseDto fromEntity(Products products){
        return ProductResponseDto.builder()
                .id(products.getId())
                .productUuid(UUID.randomUUID())
                .title(products.getTitle())
                .description(products.getDescription())
                .price(products.getPrice())
//                .productState()
                .views(products.getViews())
                .createdDate(products.getCreatedDate())
                .modifiedDate(products.getModifiedDate())
                .build();
    }

}
