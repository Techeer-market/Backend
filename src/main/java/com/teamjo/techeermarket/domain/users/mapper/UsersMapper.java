package com.teamjo.techeermarket.domain.users.mapper;

import com.teamjo.techeermarket.domain.users.dto.request.UsersSignupRequestDto;
import com.teamjo.techeermarket.domain.users.dto.response.UsersResponseDto;
import com.teamjo.techeermarket.domain.users.entity.Role;
import com.teamjo.techeermarket.domain.users.entity.Users;
//import com.teamjo.techeermarket.global.security.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UsersMapper {
    public Users toEntity(UsersSignupRequestDto usersSignupRequestDto) { //클라 -> 서버
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateTime = LocalDate.parse(usersSignupRequestDto.getBirthDay(), formatter).atStartOfDay();

        return Users.builder()
                .userUuid(UUID.randomUUID())
                .email(usersSignupRequestDto.getEmail())
                .password(usersSignupRequestDto.getPassword())
                .name(usersSignupRequestDto.getName())
                .birthday(dateTime)
//                .thumbnailUrl(usersSignupRequestDto.getThumbnailImageUrl())
                .social("local")
//                .role(Role.USER)
                .build();
    }
//
//    public Users toEntity(OAuthAttributes oAuthAttributes){
//        return Users.builder()
//                .userUuid(UUID.randomUUID())
//                .name(oAuthAttributes.getName())
//                .email(oAuthAttributes.getEmail())
//                .social(oAuthAttributes.getSocial())
////                .picture(picture)
////                .role(Role.GUEST) // 기본 권한 GUEST
//                .build();
//    }

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
