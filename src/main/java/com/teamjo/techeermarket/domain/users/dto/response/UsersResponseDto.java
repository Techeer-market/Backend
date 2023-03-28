package com.teamjo.techeermarket.domain.users.dto.response;


import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class UsersResponseDto {

    private UUID userUuid;

    private String email;

    private String password;

    private String name;

    private LocalDateTime birthDay;

    private String thumbnailUrl;

    private String social;

    private String createdDate;

    private String modifiedDate;

}
