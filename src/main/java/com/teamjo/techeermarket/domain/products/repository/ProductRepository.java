package com.teamjo.techeermarket.domain.products.repository;

import com.teamjo.techeermarket.domain.products.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Products, Long> {

//    Products findByProductUuid(UUID productUuid) ;

    @Query(value = "SELECT * FROM products WHERE is_deleted is false ", nativeQuery = true)
    Page<Products> findAllProductsWithPagination (Pageable pageable);

}
