package com.teamjo.techeermarket.domain.category.service;


import com.teamjo.techeermarket.domain.category.dto.CategoryRequestDto;
import com.teamjo.techeermarket.domain.category.dto.CategoryResponseDto;
import com.teamjo.techeermarket.domain.category.entity.Categorys;
import com.teamjo.techeermarket.domain.category.mapper.CategoryMapper;
import com.teamjo.techeermarket.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public Categorys saveCategory(CategoryRequestDto categoryRequestDto) {
        return categoryRepository.save(categoryMapper.toEntity(categoryRequestDto));
    }

    @Transactional
    public List<CategoryResponseDto> getAllCategory() {
        List<Categorys> categoryies = categoryRepository.findAll();
        return categoryies.stream()
                .map(categoryMapper::fromEntity)
                .collect(Collectors.toList());
    }

}
