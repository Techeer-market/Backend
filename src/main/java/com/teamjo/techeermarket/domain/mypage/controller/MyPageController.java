package com.teamjo.techeermarket.domain.mypage.controller;

import com.teamjo.techeermarket.domain.mypage.service.MyPageServiceImpl;
import com.teamjo.techeermarket.domain.products.dto.response.ProductPreViewDto;
import com.teamjo.techeermarket.global.config.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mypage")
public class MyPageController {

    private final MyPageServiceImpl myPageServiceImpl;

//    private final ProductRepository productRepository;

    /**
    // 내가 누른 좋아요 목록 보기
    */
    @GetMapping("/like")
    public ResponseEntity<List<ProductPreViewDto>> getLikedProducts(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        String email = userDetailsImpl.getUsername();
        List<ProductPreViewDto> likedProducts = myPageServiceImpl.getLikedProducts(email, pageNo, pageSize);
        return ResponseEntity.ok(likedProducts);
    }



    /**
    // 내가 구매한 상품 목록 보기
     */
    @GetMapping("/purchase")
    public ResponseEntity<List<ProductPreViewDto>> getMyPurchasedProducts(
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        String email = userDetailsImpl.getUsername();
        List<ProductPreViewDto> purchasedProducts = myPageServiceImpl.getMyPurchasedProducts(email, pageNo, pageSize);
        return ResponseEntity.ok(purchasedProducts);
    }


    /**
     * 나의 판매 내역 조회
     */
    @GetMapping("/sell/{userId}")
    public ResponseEntity<Map<String, List<ProductPreViewDto>>> getSellProducts(
            @PathVariable Long userId,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        String email = userDetailsImpl.getUsername();
        Map<String, List<ProductPreViewDto>> sellProducts = myPageServiceImpl.getSellProducts(userId, pageNo, pageSize);
        return ResponseEntity.ok(sellProducts);
    }


}
