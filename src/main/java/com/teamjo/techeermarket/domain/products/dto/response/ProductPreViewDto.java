package com.teamjo.techeermarket.domain.products.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
// 게시물 목차로 보여지는 리스트
public class ProductPreViewDto {

    private Long productId ;

    private String title ;

    private String thumbnailURL;  // 썸네일 이미지

    private String name;  // 작성자

    private Long userId ; // 작성자 id

    private int price ;

    private String createdAt ;

    private int likes ; // 좋아요수

    private int views;  // 뷰 수

}
