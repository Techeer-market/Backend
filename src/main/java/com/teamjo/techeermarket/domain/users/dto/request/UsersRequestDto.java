package com.teamjo.techeermarket.domain.users.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@ToString
public class UsersRequestDto {

    private String email;

    private String password;

    private String name;

    private String birthDay;

    private String thumbnailImage;

}
