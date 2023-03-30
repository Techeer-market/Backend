package com.teamjo.techeermarket.domain.products.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class ProductResponseDto {

    private Long id;

//    private Long userId;
    // 필요없다.

//    private Long categoryId;

    private UUID productUuid;

    private String title;

    private String description;

    private int price;

//    private String state;

    private int views;

    private String createdDate;

    private String modifiedDate;



}
