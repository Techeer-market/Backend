package com.teamjo.techeermarket.domain.mypage.repository;

import com.teamjo.techeermarket.domain.mypage.entity.UserPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPurchaseRepository extends JpaRepository<UserPurchase, Long> {
}

