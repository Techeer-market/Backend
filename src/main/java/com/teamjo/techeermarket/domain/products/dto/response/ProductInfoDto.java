package com.teamjo.techeermarket.domain.products.dto.response;

import com.teamjo.techeermarket.domain.products.entity.ProductState;
import com.teamjo.techeermarket.domain.products.entity.TradeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ProductInfoDto {

    private final String title;

    private int price;

//    private final String description;

    private ProductState productState;

    private TradeType tradeType;


}
