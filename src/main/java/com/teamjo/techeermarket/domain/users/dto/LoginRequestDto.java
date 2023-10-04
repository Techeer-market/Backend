package com.teamjo.techeermarket.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class LoginRequestDto {

    private String email;

    private String password;

}
