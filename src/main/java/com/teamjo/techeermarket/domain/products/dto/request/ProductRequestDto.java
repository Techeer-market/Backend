package com.teamjo.techeermarket.domain.products.dto.request;

import com.teamjo.techeermarket.domain.products.entity.ProductState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {

    private String title;

    private String description;

    private int price;

    private ProductState productState;

}
