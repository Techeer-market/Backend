package com.teamjo.techeermarket.domain.users.dto;

import com.teamjo.techeermarket.domain.users.entity.Social;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDetailResponseDto {

    private String email;

    private String name;

    private String birthday ;

    private Social social;

    private String profileUrl;
}
