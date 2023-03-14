package com.teamjo.techeermarket.domain.users.dto.response;


import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class UsersResponseDto {

    private Long id;

    private UUID userUuid;

    private String email;

    private String password;

    private String name;

    private Date birthDay;

    private String thumbnailUrl;

    private String createdDate;

    private String modifiedDate;

}
