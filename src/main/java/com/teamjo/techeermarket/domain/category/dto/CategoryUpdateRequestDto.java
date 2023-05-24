package com.teamjo.techeermarket.domain.category.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CategoryUpdateRequestDto {

    @NotEmpty
    private String name;
}
