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


import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.repository.UserNotFoundException;
import com.teamjo.techeermarket.domain.users.repository.UserRepository;
import com.teamjo.techeermarket.global.s3.S3MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    UserRepository userRepository;

    private final ProductMapper productMapper;

    private final S3MarketService s3MarketService;

    // 상품 게시물 저장
    @Transactional
    public Products postProduct(ProductRequestDto productRequestDto){

        Categorys findCategory = categoryRepository.findByCategoryUuid(productRequestDto.getCategoryUuid());
        Users findUsers = userRepository.findByUserUuid(productRequestDto.getUserUuid());

        Products product = productMapper.toEntity(productRequestDto, findCategory, findUsers);
        product.setProductState(ProductState.SALE); // ProductState를 "SALE"로 설정

        MultipartFile[] images = {
                productRequestDto.getImage_1(), productRequestDto.getImage_2(),
                productRequestDto.getImage_3(), productRequestDto.getImage_4()
        };

        for (int i = 0; i < images.length; i++) {
            MultipartFile image = images[i];
            if (image != null && !image.isEmpty()) {
                String imageUrl = s3MarketService.uploadImage(image, product.getProductUuid().toString() + "_" + (i + 1));
                switch (i) {
                    case 0:
                        product.setImage_url_1(imageUrl);
                        break;
                    case 1:
                        product.setImage_url_2(imageUrl);
                        break;
                    case 2:
                        product.setImage_url_3(imageUrl);
                        break;
                    case 3:
                        product.setImage_url_4(imageUrl);
                        break;
                }
            }
        }

        return productRepository.save(product);
    }



    // 상품 게시물 목록 페이지 전체 조회
    @Transactional(readOnly = true)
    public List<ProductInfoDto> getAllProductList(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
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


    // 카테고리별 상품 목록 페이지 전체 조회
    @Transactional(readOnly = true)
    public List<ProductInfoDto> getCategoryProductList(UUID categoryUuid, int pageNo, int pageSize) {
        Categorys category = categoryRepository.findByCategoryUuid(categoryUuid);
        if (category == null) {
            throw new IllegalArgumentException("Invalid category UUID: " + categoryUuid);
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        Page<Products> productPage = productRepository.findByCategorysAndIsDeletedFalse(category, pageable);
        return productPage.stream()
                .map(productMapper::fromListEntity)
                .collect(Collectors.toList());
    }


    // 유저가 작성한 상품 리스트 조회
    @Transactional(readOnly = true)
    public List<ProductInfoDto> getMyProductsList(UUID userUuid, int pageNo, int pageSize) {
        Users users = userRepository.findByUserUuid(userUuid);
        if (users == null) {
            throw new IllegalArgumentException("Invalid user UUID: " + userUuid);
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        Page<Products> productPage = productRepository.findByUsersAndIsDeletedFalse(users, pageable);
        return productPage.stream()
                .map(productMapper::fromListEntity)
                .collect(Collectors.toList());
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

        Users user = userRepository.findByUserUuid(productRequestDto.getUserUuid());
        if (user == null){
            throw new UserNotFoundException("User not found");
        }

        Categorys category = categoryRepository.findByCategoryUuid(productRequestDto.getCategoryUuid());
        if (category == null) {
            throw new CategoryNotFoundException("Category not found");
        }

        // 이미지 업로드
        MultipartFile[] images = {
                productRequestDto.getImage_1(), productRequestDto.getImage_2(),
                productRequestDto.getImage_3(), productRequestDto.getImage_4()
        };

        for (int i = 0; i < images.length; i++) {
            MultipartFile image = images[i];
            if (image != null && !image.isEmpty()) {
                String imageUrl = s3MarketService.uploadImage(image, products.getProductUuid().toString() + "_" + (i + 1));
                switch (i) {
                    case 0:
                        products.setImage_url_1(imageUrl);
                        break;
                    case 1:
                        products.setImage_url_2(imageUrl);
                        break;
                    case 2:
                        products.setImage_url_3(imageUrl);
                        break;
                    case 3:
                        products.setImage_url_4(imageUrl);
                        break;
                }
            }
        }


        productMapper.updateProductFromDto(products, productRequestDto, user, category);

        Products updatedProduct = productRepository.save(products);
        return productMapper.fromEntity(updatedProduct);
    }





}
