package com.teamjo.techeermarket.domain.products.service;

import com.teamjo.techeermarket.domain.category.entity.Categorys;
import com.teamjo.techeermarket.domain.category.repository.CategoryNotFoundException;
import com.teamjo.techeermarket.domain.category.repository.CategoryRepository;
import com.teamjo.techeermarket.domain.products.dto.request.ProductRequestDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductInfoDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductResponseDto;
import com.teamjo.techeermarket.domain.products.entity.ProductState;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.products.mapper.ProductMapper;
import com.teamjo.techeermarket.domain.products.repository.ProductNotFoundException;
import com.teamjo.techeermarket.domain.products.repository.ProductRepository;


import com.teamjo.techeermarket.global.s3.S3MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    public final ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private final ProductMapper productMapper;

    private final S3MarketService s3MarketService;

    // 상품 게시물 저장
    @Transactional
    public Products postProduct(ProductRequestDto productRequestDto){

        Categorys findcategory = categoryRepository.findByCategoryUuid(productRequestDto.getCategoryUuid());

        Products product = productMapper.toEntity(productRequestDto, findcategory);
        product.setProductState(ProductState.SALE); // ProductState를 "SALE"로 설정

        if (productRequestDto.getImage_1() != null && !productRequestDto.getImage_1().isEmpty()) {
            String imageUrl = s3MarketService.uploadImage(productRequestDto.getImage_1());
            product.setImage_url_1(imageUrl);
        }

        if (productRequestDto.getImage_2() != null && !productRequestDto.getImage_2().isEmpty()) {
            String imageUrl = s3MarketService.uploadImage(productRequestDto.getImage_2());
            product.setImage_url_2(imageUrl);
        }

        if (productRequestDto.getImage_3() != null && !productRequestDto.getImage_3().isEmpty()) {
            String imageUrl = s3MarketService.uploadImage(productRequestDto.getImage_3());
            product.setImage_url_3(imageUrl);
        }

        if (productRequestDto.getImage_4() != null && !productRequestDto.getImage_4().isEmpty()) {
            String imageUrl = s3MarketService.uploadImage(productRequestDto.getImage_4());
            product.setImage_url_4(imageUrl);
        }

        return productRepository.save(product);
    }


    // 상품 게시물 목록 페이지 전체 조회
    @Transactional(readOnly = true)
    public List<ProductInfoDto> getAllProductList(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Products> productPage = productRepository.findAllByIsDeletedFalse(pageable);
        return productPage.stream()
                .map(productMapper::fromListEntity)
                .collect(Collectors.toList());
    }


    // 상품 게시물 상세 조회
    @Transactional(readOnly = true)
    public ProductResponseDto getProductByUuid(UUID productUuid) {
        Products product = productRepository.findByProductUuid(productUuid);

        if (product != null) {
            return productMapper.fromEntity(product);
        } else {
            throw new ProductNotFoundException("Product not found" + productUuid);
        }
    }


    // 상품 게시물 삭제
    @Transactional
    public void deleteProduct(UUID productUuid) {
        Products products = productRepository.findByProductUuid(productUuid);
        if (products == null) {
            throw new ProductNotFoundException("Product not found" + productUuid);
        }
        products.setIsDeleted(true);
        productRepository.save(products);
    }


    // 게시물 상태 변경
    @Transactional
    public void updateProductState(UUID productUuid, String state) {
        Products products = productRepository.findByProductUuid(productUuid);

        if (products == null) {
            throw new NoSuchElementException("Product not found");
        }

        try {
            ProductState newState = ProductState.valueOf(state);
            products.setProductState(newState);
            productRepository.save(products);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid state value");
        }
    }


    // 게시물 조회수 업데이트
    @Transactional
    public void increaseViewsCount (UUID productUuid) {
        // 게시물 조회
        Products products = productRepository.findByProductUuid(productUuid);

        int views = products.getViews();
        products.setViews(views + 1);

        productRepository.save(products);
    }


    // 게시물 수정
    @Transactional
    public ProductResponseDto updateProduct(UUID productUuid, ProductRequestDto productRequestDto) {
        Products products = productRepository.findByProductUuid(productUuid);

        if (products == null) {
            throw new ProductNotFoundException("Product not found");
        }

        Categorys category = categoryRepository.findByCategoryUuid(productRequestDto.getCategoryUuid());
        if (category == null) {
            throw new CategoryNotFoundException("Category not found");
        }

        productMapper.updateProductFromDto(products, productRequestDto, category);

        Products updatedProduct = productRepository.save(products);
        return productMapper.fromEntity(updatedProduct);
    }





}
