package com.teamjo.techeermarket.domain.users.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
public class UsersSignupRequestDto {

    private Long id;
    private String email;

    private String password;

    private String name;

    private String birthDay;


    private MultipartFile thumbnailImage;

    // TODO 추후 수정할 것 > 클라이언트 요청 정보가 아니므로 분리해야 함
    private String thumbnailImageUrl;

}
