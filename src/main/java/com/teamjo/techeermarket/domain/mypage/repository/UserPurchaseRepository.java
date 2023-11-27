package com.teamjo.techeermarket.domain.mypage.repository;

import com.teamjo.techeermarket.domain.mypage.entity.UserPurchase;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.users.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPurchaseRepository extends JpaRepository<UserPurchase, Long> {
    boolean existsBySellerIdAndProducts(Users sellerId, Products products);

    Page<UserPurchase> findByBuyerId(Users buyerId, Pageable pageable);
//    List<UserPurchase> findByBuyer(Users buyer, Pageable pageable);
}

