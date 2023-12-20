package com.teamjo.techeermarket.domain.products.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailViewDto {

    private List<String> productImages;

    private String name ; //작성자

    private String createdAt ;






}
