package com.teamjo.techeermarket.domain.products.controller;

import com.teamjo.techeermarket.domain.products.dto.request.ProductRequestDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductResponseDto;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.products.mapper.ProductMapper;
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

    private final ProductApiController productApiController;
    private final ProductMapper productMapper;

    @PostMapping
    public ProductResponseDto postProduct(@Validated @RequestBody ProductRequestDto productRequestDto){

        Products product = productApiController.postProduct(productRequestDto);
        return productMapper.fromEntity(product);
    }
}
