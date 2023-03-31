package com.teamjo.techeermarket.domain.products.service;

import com.teamjo.techeermarket.domain.products.dto.request.ProductRequestDto;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.products.mapper.ProductMapper;
import com.teamjo.techeermarket.domain.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductAPiService {

    public final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Transactional
    public Products postProduct(ProductRequestDto productRequestDto){
        return productRepository.save(productMapper.toEntity(productRequestDto));
    }
}
