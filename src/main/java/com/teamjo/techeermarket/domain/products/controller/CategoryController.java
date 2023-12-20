package com.teamjo.techeermarket.domain.products.controller;

import com.teamjo.techeermarket.domain.products.dto.response.ProductPreViewDto;
import com.teamjo.techeermarket.domain.products.mapper.ProductMapper;
import com.teamjo.techeermarket.domain.products.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/category")
public class CategoryController {


    private final CategoryService categoryService ;
    private final ProductMapper productMapper;

    /**
     // 카테고리별 상품 목록 조회
     */
    @GetMapping("/list/{categoryId}")
    public ResponseEntity<List<ProductPreViewDto>> getCategoryListByPagination(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        List<ProductPreViewDto> productsList = categoryService.getCategoryList(categoryId, pageNo, pageSize);
        return ResponseEntity.ok(productsList);
    }


    /**
    // 카테고리 내에서 제목으로 상품 게시물 검색
    */
    @GetMapping("/{categoryId}")
    public ResponseEntity<List<ProductPreViewDto>>  searchProductsByTitleInCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam String search ) {
        Page<ProductPreViewDto> searchResult = categoryService.searchByTitleInCategory(categoryId, pageNo, pageSize, search);

        List<ProductPreViewDto> productList = searchResult.getContent();
        return ResponseEntity.ok(productList);
    }



}
