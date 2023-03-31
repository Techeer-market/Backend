package com.teamjo.techeermarket.domain.products.controller;

import com.teamjo.techeermarket.domain.products.dto.request.ProductRequestDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductResponseDto;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.products.mapper.ProductMapper;
import com.teamjo.techeermarket.domain.products.service.ProductAPiService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductApiController {

    private final ProductAPiService productApiService;
    private final ProductMapper productMapper;

    // 차후에 세션 추가해서 User 정보 product entity에 저장하기
    @PostMapping
    public ProductResponseDto postProduct(@Validated @RequestBody ProductRequestDto productRequestDto){

        Products product = productApiService.postProduct(productRequestDto);
        return productMapper.fromEntity(product);
    }
}
