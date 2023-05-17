package com.teamjo.techeermarket.domain.products.controller;

import com.teamjo.techeermarket.domain.products.dto.request.ProductRequestDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductInfo;
import com.teamjo.techeermarket.domain.products.dto.response.ProductPageDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductResponseDto;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.products.mapper.ProductMapper;
import com.teamjo.techeermarket.domain.products.service.ProductAPiService;
import com.teamjo.techeermarket.global.common.PageInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

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

    // 목차 상품 목록 : 제목, 가격, 상태 확인 가능
    @GetMapping("/list")
    public ResponseEntity<List<ProductInfo>> getAllProductsListByPagnation(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        List<ProductInfo> productsList = productApiService.getAllProductList(pageNo, pageSize);
        return ResponseEntity.ok(productsList);
    }




}
