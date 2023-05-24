package com.teamjo.techeermarket.domain.category.controller;

import com.teamjo.techeermarket.domain.category.dto.CategoryRequestDto;
import com.teamjo.techeermarket.domain.category.dto.CategoryResponseDto;
import com.teamjo.techeermarket.domain.category.dto.CategoryUpdateRequestDto;
import com.teamjo.techeermarket.domain.category.entity.Categorys;
import com.teamjo.techeermarket.domain.category.mapper.CategoryMapper;
import com.teamjo.techeermarket.domain.category.repository.CategoryNotFoundException;
import com.teamjo.techeermarket.domain.category.repository.CategoryRepository;
import com.teamjo.techeermarket.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/categorys")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    private final CategoryRepository categoryRepository;

    // 카테고리 추가
    @PostMapping()
    public CategoryResponseDto saveCategory (@RequestBody CategoryRequestDto categoryRequestDto) {
        Categorys saveCategorys = categoryService.saveCategory(categoryRequestDto);
        return categoryMapper.fromEntity(saveCategorys);
    }

    // 카테고리 조회
    @GetMapping()
    public ResponseEntity<List<CategoryResponseDto>> getCategory() {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    // 카테고리 수정
    @PutMapping("/{categoryUuid}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable UUID categoryUuid, @Valid @RequestBody CategoryUpdateRequestDto updateDto) {
        categoryService.updateCategory(categoryUuid, updateDto);
        Categorys updatedCategory = categoryRepository.findByCategoryUuid(categoryUuid);
        CategoryResponseDto responseDto = categoryMapper.fromEntity(updatedCategory);
        return ResponseEntity.ok(responseDto);
    }

    // 카테고리 삭제
    @DeleteMapping("/{categoryUuid}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID categoryUuid) {
        categoryService.deleteCategory(categoryUuid);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> handleCategoryNotFoundException(CategoryNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

}
