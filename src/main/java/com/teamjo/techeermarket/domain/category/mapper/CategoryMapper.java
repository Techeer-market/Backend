package com.teamjo.techeermarket.domain.category.mapper;

import com.teamjo.techeermarket.domain.category.dto.CategoryRequestDto;
import com.teamjo.techeermarket.domain.category.dto.CategoryResponseDto;
import com.teamjo.techeermarket.domain.category.entity.Categorys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    public Categorys toEntity(CategoryRequestDto categoryRequestDto){
        return Categorys.builder()
                .name(categoryRequestDto.getName())
                .categoryUuid(UUID.randomUUID())
                .build();
    }


    public CategoryResponseDto fromEntity(Categorys categorys){
        return CategoryResponseDto.builder()

                .categoryUuid(categorys.getCategoryUuid())
                .name(categorys.getName())
                .createdDate(categorys.getCreatedDate())
                .modifiedDate(categorys.getModifiedDate())
                .build();
    }

}
