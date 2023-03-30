package com.teamjo.techeermarket.domain.products.repository;

import com.teamjo.techeermarket.domain.products.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Products, Long> {
}
