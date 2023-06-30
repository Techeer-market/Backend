package com.teamjo.techeermarket.domain.products.controller;

import com.teamjo.techeermarket.domain.products.dto.request.ProductRequestDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductInfoDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductResponseDto;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.products.mapper.ProductMapper;
import com.teamjo.techeermarket.domain.products.repository.ProductNotFoundException;
import com.teamjo.techeermarket.domain.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    // 차후에 세션 추가해서 User 정보 product entity에 저장하기
    @PostMapping
    public ProductResponseDto postProduct(@Validated @ModelAttribute ProductRequestDto productRequestDto){
        log.info("ProductRequestDto :: {} ", productRequestDto);
        Products product = productService.postProduct(productRequestDto);
        return productMapper.fromEntity(product);
    }


    // 목차 상품 목록 조회 : 제목, 가격, 상태, 사진1 확인 가능
    @GetMapping("/list")
    public ResponseEntity<List<ProductInfoDto>> getAllProductsListByPagnation(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "25") int pageSize) {
        List<ProductInfoDto> productsList = productService.getAllProductList(pageNo, pageSize);
        return ResponseEntity.ok(productsList);
    }

    // 상품 게시물 상세 조회
    @GetMapping("/list/{productUuid}")
    public ResponseEntity<ProductResponseDto> getProductAndIncreaseViews(@PathVariable UUID productUuid) {
        ProductResponseDto product = productService.getProductByUuid(productUuid);
        if (product != null) {
            // 조회수 증가
            productService.increaseViewsCount(productUuid);
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // 카테고리별 상품 목록 조회
    @GetMapping("/category/list/{categoryUuid}")
    public ResponseEntity<List<ProductInfoDto>> getCategoryProductListByPagination(
            @PathVariable UUID categoryUuid,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "25") int pageSize) {
        List<ProductInfoDto> productsList = productService.getCategoryProductList(categoryUuid, pageNo, pageSize);
        return ResponseEntity.ok(productsList);
    }


    // 유저별 상품 목록 조회
    @GetMapping("/my/{userUuid}")
    public ResponseEntity<List<ProductInfoDto>> getMyProductsListByPagination(
            @PathVariable UUID userUuid,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "25") int pageSize) {
        List<ProductInfoDto> productsList = productService.getMyProductsList(userUuid, pageNo, pageSize);
        return ResponseEntity.ok(productsList);
    }


    // 상품 게시물 삭제
    @DeleteMapping("/{productUuid}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productUuid) {
        productService.deleteProduct(productUuid);
        return ResponseEntity.ok().build();
    }


    // 상품 게시물 상태 변경
    @PutMapping("/{productUuid}/state")
    public ResponseEntity<Void> updateProductState(
            @PathVariable UUID productUuid,
            @RequestBody Map<String, String> request) {
        String state = request.get("state");

        productService.updateProductState(productUuid, state);
        return ResponseEntity.ok().build();

    }



    // 게시물 수정
    @PutMapping("/{productUuid}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable UUID productUuid,
            @ModelAttribute ProductRequestDto productRequestDto) {
        try {
            ProductResponseDto updatedProduct = productService.updateProduct(productUuid, productRequestDto);
            return ResponseEntity.ok(updatedProduct);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }




}
