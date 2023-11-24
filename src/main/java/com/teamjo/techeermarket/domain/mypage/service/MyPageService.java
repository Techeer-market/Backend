package com.teamjo.techeermarket.domain.mypage.service;

import com.teamjo.techeermarket.domain.mypage.repository.UserLikeRepository;
import com.teamjo.techeermarket.domain.products.entity.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserLikeRepository userLikeRepository;

    /*
    // 게시물 좋아요 수 계산하기
    */
//    @Transactional(readOnly = true)
    // 상품에 대한 좋아요 개수 조회
//    public int getLikesCountByProduct(Products product) {
//        return userLikeRepository.countByProduct(product);
//    }




}
