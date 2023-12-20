package com.teamjo.techeermarket.domain.products.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ProductUpdateRequestDto {

    private String title;

    private String content;

    private String categoryName;

    private int price;

    private String location;

    private List<String> existProductImages;

    private List<MultipartFile> newProductImages;  // 새로 들어온 jpg

    private List<Integer> imageOrder;

}
