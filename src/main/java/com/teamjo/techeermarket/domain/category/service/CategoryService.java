package com.teamjo.techeermarket.domain.category.service;


import com.amazonaws.services.kms.model.NotFoundException;
import com.teamjo.techeermarket.domain.category.dto.CategoryRequestDto;
import com.teamjo.techeermarket.domain.category.dto.CategoryResponseDto;
import com.teamjo.techeermarket.domain.category.dto.CategoryUpdateRequestDto;
import com.teamjo.techeermarket.domain.category.entity.Categorys;
import com.teamjo.techeermarket.domain.category.mapper.CategoryMapper;
import com.teamjo.techeermarket.domain.category.repository.CategoryNotFoundException;
import com.teamjo.techeermarket.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;


    @Transactional
    public Categorys saveCategory(CategoryRequestDto categoryRequestDto) {
        return categoryRepository.save(categoryMapper.toEntity(categoryRequestDto));
    }


    @Transactional
    public List<CategoryResponseDto> getAllCategory() {
        List<Categorys> categoryies = categoryRepository.findAllByIsDeletedFalse();
        return categoryies.stream()
                .map(categoryMapper::fromEntity)
                .collect(Collectors.toList());
    }



    @Transactional
    public void updateCategory(UUID categoryUuid, CategoryUpdateRequestDto updateDto){
        Categorys category = categoryRepository.findByCategoryUuid(categoryUuid);
        category.update(updateDto.getName());
        categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(UUID categoryUuid) {
        Categorys category = categoryRepository.findByCategoryUuid(categoryUuid);
        if (category == null) {
            throw new CategoryNotFoundException("Category not found " + categoryUuid);
        }
        category.setIsDeleted(true);
        categoryRepository.save(category);
    }



}
