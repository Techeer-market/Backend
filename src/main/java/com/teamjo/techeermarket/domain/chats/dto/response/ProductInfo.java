package com.teamjo.techeermarket.domain.chats.dto.response;

import com.teamjo.techeermarket.domain.products.entity.ProductState;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfo {
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
