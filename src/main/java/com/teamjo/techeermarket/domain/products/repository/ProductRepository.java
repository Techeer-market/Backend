package com.teamjo.techeermarket.domain.products.repository;

import com.teamjo.techeermarket.domain.products.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Products, Long> {
//    Products findByProductUuid(UUID productUuid) ;

}
