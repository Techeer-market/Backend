package com.teamjo.techeermarket.domain.users.mapper;

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
}
