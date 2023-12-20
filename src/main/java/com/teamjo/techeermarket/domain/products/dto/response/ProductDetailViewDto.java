package com.teamjo.techeermarket.domain.products.dto.response;

import com.teamjo.techeermarket.domain.products.entity.ProductState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailViewDto {

    private Long productId ;

    private List<String> productImages;

    private String name ; //작성자

    private Long userId ; // 작성자 id

    private String categoryName ; // 카테고리 이름

    private String title ;

    private String content ;

    private int price ;

    private ProductState productState;  // 상품 상태

    private int likes ; // 좋아요수

    private int views;  // 뷰 수

    private String createdAt ;

    private String updatedAt ;


}
