package com.teamjo.techeermarket.domain.products.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {

    @NotEmpty(message = "제목을 입력해주세요.")
    private String title;

    @NotEmpty(message = "상품 정보 입력은 필수 입니다.")
    private String content;

    private String categoryName;

    @NotEmpty(message = "가격 입력은 필수 입니다.")
    private String price;

    private List<MultipartFile> productImages;

}
