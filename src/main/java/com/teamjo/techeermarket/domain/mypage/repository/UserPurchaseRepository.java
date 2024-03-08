package com.teamjo.techeermarket.domain.mypage.repository;

import com.teamjo.techeermarket.domain.mypage.entity.UserPurchase;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.users.entity.Users;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserPurchaseRepository extends JpaRepository<UserPurchase, Long> {
    boolean existsBySellerIdAndProducts(Users sellerId, Products products);

    Page<UserPurchase> findByBuyerId(Users buyerId, Pageable pageable);

    @Query("SELECT p FROM UserPurchase p WHERE p.products.id = :productId")
    Optional<UserPurchase> findUserPurchaseByProducts(@Param("productId") Long productId);

    void deleteByProducts(Products products);

//    List<UserPurchase> findByBuyer(Users buyer, Pageable pageable);

}

