package com.teamjo.techeermarket.domain.products.dto.response;

import com.teamjo.techeermarket.domain.products.entity.ProductState;
import com.teamjo.techeermarket.domain.products.entity.TradeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
// 상품 목록을 조회하는 DTO
public class ProductInfoDto {

    private UUID productUuid;

    private final String title;

    private int price;

    private String image_url_1;

    private ProductState productState;

    private TradeType tradeType;


}
