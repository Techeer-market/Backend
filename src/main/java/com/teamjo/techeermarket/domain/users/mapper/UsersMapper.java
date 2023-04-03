package com.teamjo.techeermarket.domain.users.mapper;

import com.teamjo.techeermarket.domain.users.dto.request.UsersRequestDto;
import com.teamjo.techeermarket.domain.users.dto.response.UsersResponseDto;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.global.security.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UsersMapper {
    public Users toEntity(UsersRequestDto usersRequestDto) { //클라 -> 서버
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime dateTime = LocalDateTime.parse(usersRequestDto.getBirthDay()+" 00:00:00.000", formatter);

        return Users.builder()
                .userUuid(UUID.randomUUID())
                .email(usersRequestDto.getEmail())
                .password(usersRequestDto.getPassword())
                .name(usersRequestDto.getName())
                .birthday(dateTime)
                .thumbnailUrl(usersRequestDto.getThumbnailImageUrl())
                .social("local")
                .build();
    }

    public Users toEntity(OAuthAttributes oAuthAttributes){
        return Users.builder()
                .userUuid(UUID.randomUUID())
                .name(oAuthAttributes.getName())
                .email(oAuthAttributes.getEmail())
                .social(oAuthAttributes.getSocial())
//                .picture(picture)
//                .role(Role.GUEST) // 기본 권한 GUEST
                .build();
    }

    public UsersResponseDto fromEntity(Users users) {
        return UsersResponseDto.builder()
                .userUuid(users.getUserUuid())
                .email(users.getEmail())
                .password(users.getPassword())
                .name(users.getName())
                .birthDay(users.getBirthday())
                .thumbnailUrl(users.getThumbnailUrl())
                .social(users.getSocial())
                .createdDate(users.getCreatedDate())
                .modifiedDate(users.getModifiedDate())
                .build();
    }
}
