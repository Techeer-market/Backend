package com.teamjo.techeermarket.domain.products.mapper;

import com.teamjo.techeermarket.domain.products.dto.request.ProductRequestDto;
import com.teamjo.techeermarket.domain.products.entity.Categorys;
import com.teamjo.techeermarket.domain.products.entity.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    public Products saveToEntity(ProductRequestDto productRequestDto, Categorys categorysId) {
        return Products.builder()
                .title(productRequestDto.getTitle())
                .content(productRequestDto.getContent())
                .categorys(categorysId)
                .price(productRequestDto.getPrice())
                .build();
    }
}
