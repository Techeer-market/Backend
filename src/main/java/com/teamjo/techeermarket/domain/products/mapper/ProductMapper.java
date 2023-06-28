package com.teamjo.techeermarket.domain.products.mapper;


import com.teamjo.techeermarket.domain.category.entity.Categorys;
import com.teamjo.techeermarket.domain.products.dto.request.ProductRequestDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductInfoDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductResponseDto;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.users.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    public Products toEntity(ProductRequestDto productRequestDto, Categorys categorys, Users users){
        return Products.builder()
//                .users(users)
                .title(productRequestDto.getTitle())
                .productUuid(UUID.randomUUID())
                .users(users)  //유저
                .categorys(categorys)               // 카테고리!
                .description(productRequestDto.getDescription())
                .price(productRequestDto.getPrice())
                .tradeType(productRequestDto.getTradeType())
                .build();
    }

    public ProductResponseDto fromEntity(Products products){
        return ProductResponseDto.builder()
                .productUuid(products.getProductUuid())
//                .categoryUuid(products.getCategorys().getCategoryUuid())   //카테고리!
                .userEmail(products.getUsers().getEmail())
                .categoryName(products.getCategorys().getName())  // 카테고리 !
                .title(products.getTitle())
                .description(products.getDescription())
                .price(products.getPrice())
                .productState(products.getProductState())
                .tradeType(products.getTradeType())
                .views(products.getViews())
                .image_url_1(products.getImage_url_1())
                .image_url_2(products.getImage_url_2())
                .image_url_3(products.getImage_url_3())
                .image_url_4(products.getImage_url_4())
                .createdDate(products.getCreatedDate())
                .modifiedDate(products.getModifiedDate())
                .build();
    }

    public ProductInfoDto fromListEntity(Products products){
        return ProductInfoDto.builder()
                .productUuid(products.getProductUuid())
                .title(products.getTitle())
                .price(products.getPrice())
                .productState(products.getProductState())
                .tradeType(products.getTradeType())
                .image_url_1(products.getImage_url_1())
                .build();
    }


    public void updateProductFromDto(Products product, ProductRequestDto productRequestDto, Users users, Categorys categorys) {
        product.setTitle(productRequestDto.getTitle());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setTradeType(productRequestDto.getTradeType());
        product.setUsers(users);
        product.setCategorys(categorys);
    }

}
