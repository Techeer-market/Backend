package com.teamjo.techeermarket.global.jwt;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class VerifyResultDto {

    private boolean success;

    private String userEmail;


}
