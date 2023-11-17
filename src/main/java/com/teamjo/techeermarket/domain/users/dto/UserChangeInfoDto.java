package com.teamjo.techeermarket.domain.users.dto;

import com.teamjo.techeermarket.domain.users.entity.Social;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
// 회원 정보 수정
public class UserChangeInfoDto {

    private String email;

    private String password;

    private String birthday ;


}
