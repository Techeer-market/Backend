package com.teamjo.techeermarket.domain.category.controller;

import com.teamjo.techeermarket.domain.category.dto.CategoryRequestDto;
import com.teamjo.techeermarket.domain.category.dto.CategoryResponseDto;
import com.teamjo.techeermarket.domain.category.entity.Categorys;
import com.teamjo.techeermarket.domain.category.mapper.CategoryMapper;
import com.teamjo.techeermarket.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorys")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    private final CategoryMapper categoryMapper;

    @PostMapping()
    public CategoryResponseDto saveCategory (@RequestBody CategoryRequestDto categoryRequestDto) {
        Categorys saveCategorys = categoryService.saveCategory(categoryRequestDto);
        return categoryMapper.fromEntity(saveCategorys);
    }



}
