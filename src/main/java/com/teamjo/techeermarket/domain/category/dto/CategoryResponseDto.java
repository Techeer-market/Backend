package com.teamjo.techeermarket.domain.category.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class CategoryResponseDto {

    private Long id;

    private UUID categoryUuid;

    private String name;

    private String createdDate;

    private String modifiedDate;


}
