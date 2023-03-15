package com.teamjo.techeermarket.domain.users.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class UsersRequestDto {

    private String email;

    private String password;

    private String name;

    private Date birthDay;

    private String thumbnailImage;
}
