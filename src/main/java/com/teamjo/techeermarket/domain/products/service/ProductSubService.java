package com.teamjo.techeermarket.domain.products.service;

import com.teamjo.techeermarket.domain.products.entity.ProductState;

public interface ProductSubService {

    void updateProductSeller(String email, Long productId);

    void increaseViewCount(Long productId);

    void updateProductState(Long productId, ProductState newState, String email, String buyerEmail);

    void likeProduct(String email, Long productId);

    void unlikeProduct(String email, Long productId);

    void increaseViewsCount(Long productId);
}
