package com.teamjo.techeermarket.domain.users.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@ToString
public class UsersRequestDto {

    @NotBlank(message = "email cannot be blank")
    private String email;

    @NotBlank(message = "password cannot be blank")
    private String password;

    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotBlank(message = "birthDay cannot be blank")
    private String birthDay;

    private MultipartFile thumbnailImage;

    // TODO 추후 수정할 것 > 클라이언트 요청 정보가 아니므로 분리해야 함
    private String thumbnailImageUrl;

}
