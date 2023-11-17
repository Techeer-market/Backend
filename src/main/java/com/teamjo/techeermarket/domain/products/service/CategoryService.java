package com.teamjo.techeermarket.domain.products.service;

import com.teamjo.techeermarket.domain.products.dto.response.ProductPreViewDto;
import com.teamjo.techeermarket.domain.products.entity.Categorys;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.products.mapper.ProductMapper;
import com.teamjo.techeermarket.domain.products.repository.CategoryRepository;
import com.teamjo.techeermarket.domain.products.repository.ProductRepository;
import com.teamjo.techeermarket.global.exception.product.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;
    private final ProductMapper productMapper;



    /*
    // 카테고리 별 상품 게시물 목록 조회
     */
    @Transactional(readOnly = true)
    public List<ProductPreViewDto> getCategoryList (Long categoryId, int pageNo, int pageSize) {
        Categorys category = categoryRepository.findCategorysById(categoryId);
        if (category == null) {
            throw new CategoryNotFoundException();
        }
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("id").descending());  // 1페이지부터 시작하도록
        Page<Products> productPage = productRepository.findByCategorys(category,pageable);
        return productPage.stream()
                .map(productMapper::fromListEntity)
                .collect(Collectors.toList());
    }



}
