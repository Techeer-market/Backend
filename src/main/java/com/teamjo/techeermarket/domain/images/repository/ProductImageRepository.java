package com.teamjo.techeermarket.domain.images.repository;

import com.teamjo.techeermarket.domain.images.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

}
