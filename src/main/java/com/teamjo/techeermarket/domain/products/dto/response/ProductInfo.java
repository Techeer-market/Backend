package com.teamjo.techeermarket.domain.products.dto.response;

import com.teamjo.techeermarket.domain.products.entity.ProductState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ProductInfo {

    private final String title;

    private int price;

//    private final String description;

    private ProductState productState;


}
