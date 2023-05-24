package com.teamjo.techeermarket.domain.users.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@ToString
public class UsersLoginRequestDto {
    private String email;
    private String password;
}
