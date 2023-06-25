package com.teamjo.techeermarket.domain.users.dto.request;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
public class UsersLoginRequestDto {
    private String email;
    private String password;
}
