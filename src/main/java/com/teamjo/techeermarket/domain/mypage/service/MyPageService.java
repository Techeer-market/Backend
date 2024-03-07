package com.teamjo.techeermarket.domain.mypage.service;

import com.teamjo.techeermarket.domain.products.dto.response.ProductPreViewDto;
import com.teamjo.techeermarket.domain.products.entity.ProductState;
import com.teamjo.techeermarket.domain.users.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


public interface MyPageService {
    void likeProduct(String email, Long productId);

    void unlikeProduct(String email, Long productId);

    List<ProductPreViewDto> getLikedProducts(String email, int pageNo, int pageSize);

    List<ProductPreViewDto> getMyPurchasedProducts(String email, int pageNo, int pageSize);

    Map<String, List<ProductPreViewDto>> getSellProducts(Long userId, int pageNo, int pageSize);

    // 특정 상태의 상품 목록 조회 메서드
    List<ProductPreViewDto> getProductsByState(Users findUsers, List<ProductState> states, Pageable pageable);

}
