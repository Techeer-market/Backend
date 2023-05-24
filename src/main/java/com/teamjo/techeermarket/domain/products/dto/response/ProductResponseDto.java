package com.teamjo.techeermarket.domain.products.dto.response;

import com.teamjo.techeermarket.domain.products.entity.ProductState;
import com.teamjo.techeermarket.domain.products.entity.TradeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class ProductResponseDto {

//    private Long id;

//    private Long userId;

//    private UUID categoryUuid;

    private String categoryName;

    private UUID productUuid;

    private String title;

    private String description;

    private int price;

    private ProductState productState;

    private TradeType tradeType;

    private int views;

    private String createdDate;

    private String modifiedDate;


}
