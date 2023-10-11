package com.teamjo.techeermarket.domain.users.mapper;

import com.teamjo.techeermarket.domain.users.dto.SignUpRequestDto;
import com.teamjo.techeermarket.domain.users.dto.UserDetailResponseDto;
import com.teamjo.techeermarket.domain.users.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Users toEntity(SignUpRequestDto dto);
    SignUpRequestDto toDto(Users entity);

    UserDetailResponseDto fromEntity(Users users);

}

