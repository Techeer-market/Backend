package com.teamjo.techeermarket.domain.users.mapper;

import com.teamjo.techeermarket.domain.products.dto.request.ProductRequestDto;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.users.dto.SignUpRequestDto;
import com.teamjo.techeermarket.domain.users.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Users toEntity(SignUpRequestDto dto);
    SignUpRequestDto toDto(Users entity);

}

