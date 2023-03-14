package com.teamjo.techeermarket.domain.users.mapper;

import com.teamjo.techeermarket.domain.users.dto.request.UsersRequestDto;
import com.teamjo.techeermarket.domain.users.dto.response.UsersResponseDto;
import com.teamjo.techeermarket.domain.users.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsersMapper {
    public Users toEntity(UsersRequestDto usersRequestDto) { //클라 -> 서버
        return Users.builder()
                .email(usersRequestDto.getEmail())
                .password(usersRequestDto.getPassword())
                .name(usersRequestDto.getName())
                .birthday(usersRequestDto.getBirthDay())
                .thumbnailUrl(usersRequestDto.getThumbnailImage())
                .build();
    }

    public UsersResponseDto fromEntity(Users users) {
        return UsersResponseDto.builder()
                .id(users.getId())
                .userUuid(users.getUserUuid())
                .email(users.getEmail())
                .password(users.getPassword())
                .name(users.getName())
                .birthDay(users.getBirthday())
                .thumbnailUrl(users.getThumbnailUrl())
                .createdDate(users.getCreatedDate())
                .modifiedDate(users.getModifiedDate())
                .build();
    }
}
