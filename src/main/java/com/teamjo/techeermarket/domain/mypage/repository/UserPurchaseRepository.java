package com.teamjo.techeermarket.domain.mypage.repository;

import com.teamjo.techeermarket.domain.mypage.entity.UserPurchase;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPurchaseRepository extends JpaRepository<UserPurchase, Long> {
    boolean existsBySellerIdAndProducts(Users sellerId, Products products);

}

