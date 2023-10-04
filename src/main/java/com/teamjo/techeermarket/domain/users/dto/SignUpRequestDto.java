package com.teamjo.techeermarket.domain.users.dto;

import com.teamjo.techeermarket.domain.users.entity.Social;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SignUpRequestDto {

    private String email;

    private String name;

    private String password;

    private String birthday ;

    @Builder.Default
    private Social social = Social.LOCAl;

}
