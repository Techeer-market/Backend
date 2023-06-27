package com.teamjo.techeermarket.domain.products.dto.request;

import com.teamjo.techeermarket.domain.products.entity.ProductState;
import com.teamjo.techeermarket.domain.products.entity.TradeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {

    private UUID categoryUuid;

    private String title;

    private String description;

    private int price;

    private TradeType tradeType ;

    private MultipartFile image_1;

    private MultipartFile image_2;

    private MultipartFile image_3;

    private MultipartFile image_4;


}
