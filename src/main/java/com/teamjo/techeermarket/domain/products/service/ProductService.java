package com.teamjo.techeermarket.domain.products.service;

import com.teamjo.techeermarket.domain.images.service.ProductImageService;
import com.teamjo.techeermarket.domain.products.dto.request.ProductRequestDto;
import com.teamjo.techeermarket.domain.products.entity.Categorys;
import com.teamjo.techeermarket.domain.products.entity.ProductState;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.products.mapper.ProductMapper;
import com.teamjo.techeermarket.domain.products.repository.CategoryRepository;
import com.teamjo.techeermarket.domain.products.repository.ProductRepository;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService  {

    private final ProductRepository productRepository;
    private final ProductImageService productImageService;
    @Autowired
    private final UserRepository userRepository;

    private final ProductMapper productMapper;
    @Autowired
    private final CategoryRepository categoryRepository;


    // 상품 저장 ( = 게시물 저장)
    @Transactional
    public Long saveProduct(ProductRequestDto request, String email) {
        Users findUsers = userRepository.findByEmail(email);
        Categorys findCategory = categoryRepository.findIdByName(request.getCategoryName());  // 정상 작동 X

        Products products = productMapper.saveToEntity(request, findCategory);
        products.setUsers(findUsers);
        products.setViews(0);
        products.setProductState(ProductState.SALE); // ProductState를 "SALE"로 설정

        productRepository.save(products);
//        updateThumbnail(getProductImage(request.getProductImages(), product), product.getId());

        return products.getId();
    }


    // 게시물 전체 조회



}