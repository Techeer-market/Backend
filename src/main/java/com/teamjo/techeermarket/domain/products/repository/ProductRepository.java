package com.teamjo.techeermarket.domain.products.repository;

import com.teamjo.techeermarket.domain.products.entity.Categorys;
import com.teamjo.techeermarket.domain.products.entity.ProductState;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.users.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Products, Long> {

    Page<Products> findByCategorys(Categorys category, Pageable pageable);
    @Modifying
    @Query("UPDATE Products p SET p.heart = p.heart + 1 WHERE p = :product")
    void incrementHeartCount(@Param("product") Products product);

    @Modifying
    @Query("UPDATE Products p SET p.heart = p.heart - 1 WHERE p = :product")
    void decrementHeartCount(@Param("product") Products product);

    Page<Products> findByIdIn(Set<Long> productIds, Pageable pageable);

    Page<Products> findByCategorysAndTitleContainingIgnoreCaseOrderByIdDesc(Categorys category, String title, Pageable pageable);

    Page<Products> findAllByTitleContainingOrderByIdDesc(String search, Pageable pageable);

    Page<Products> findByProductStateNot(ProductState sold, Pageable pageable);

    @Query("select p from Products p join fetch p.users u where u = :findUsers and p.productState in :states")
    List<Products> findByUsersAndProductStateInWithUserFetch(@Param("findUsers") Users findUsers, @Param("states") List<ProductState> states, Pageable pageable);

}