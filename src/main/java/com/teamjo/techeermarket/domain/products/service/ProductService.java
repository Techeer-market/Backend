package com.teamjo.techeermarket.domain.products.service;

import com.teamjo.techeermarket.domain.products.dto.request.ProductRequestDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductDetailViewDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductPreViewDto;
import com.teamjo.techeermarket.domain.products.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    Long saveProduct(ProductRequestDto request, String email) throws IOException;

    @Transactional(readOnly = true)
    List<ProductPreViewDto> getAllProductList(int pageNo, int pageSize);

    @Transactional(readOnly = true)
    List<ProductPreViewDto> getListExceptSold(int pageNo, int pageSize);

    @Transactional(readOnly = true)
    ProductDetailViewDto getProductDetail(String email, Long productId);

    @Transactional
    void deleteProduct(Long productId, String userEmail);

    @Transactional(readOnly = true)
    Page<ProductPreViewDto> searchProductsTitle(int pageNo, int pageSize, String search);

    @Transactional
    Long updateProduct(Long productId, ProductRequestDto updateRequest, String email) throws IOException;

    void setProductImg(Products products, List<String> imageUrls, Long newProductID);
}
