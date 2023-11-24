package com.teamjo.techeermarket.domain.mypage.repository;

import com.teamjo.techeermarket.domain.mypage.entity.UserLike;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLikeRepository extends JpaRepository<UserLike, Long> {
    boolean existsByUsersAndProducts(Users users, Products products);

    Optional<UserLike> findByUsersAndProducts(Users users, Products products);

//    int countByProduct(Products products);
}
