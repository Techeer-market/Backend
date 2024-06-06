package com.teamjo.techeermarket.domain.users.mapper;

import com.teamjo.techeermarket.domain.users.dto.SignUpRequestDto;
import com.teamjo.techeermarket.domain.users.dto.UserDetailResponseDto;
import com.teamjo.techeermarket.domain.users.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFromMapper {

    public static UserDetailResponseDto fromEntity(Users users) {
        return UserDetailResponseDto.builder()
                .email(users.getEmail())
                .name(users.getName())
                .birthday(users.getBirthday())
                .social(users.getSocial())
                .profileUrl(users.getProfileUrl())
                .build();
    }

    public static Users toEntity(SignUpRequestDto dto) {
        return Users.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .birthday(dto.getBirthday())
                .social(dto.getSocial())
                .build();
    }

}
