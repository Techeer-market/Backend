package com.teamjo.techeermarket.domain.products.service;

import com.teamjo.techeermarket.domain.images.entity.ProductImage;
import com.teamjo.techeermarket.domain.images.repository.ProductImageRepository;
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
import com.teamjo.techeermarket.global.S3.BucketDir;
import com.teamjo.techeermarket.global.S3.S3Service;
import com.teamjo.techeermarket.global.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService  {

    private final ProductRepository productRepository;
    private final ProductImageService productImageService;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ProductMapper productMapper;
    @Autowired
    private final S3Service s3Service;
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final ProductImageRepository productImageRepository;


    // 상품 저장 ( = 게시물 저장)
    @Transactional
    public Long saveProduct(ProductRequestDto request, String email) throws IOException {
        Users findUsers = userRepository.findByEmail(email);
        Categorys findCategory = categoryRepository.findIdByName(request.getCategoryName());

        Products products = productMapper.saveToEntity(request, findCategory);
        products.setUsers(findUsers);
        products.setViews(0);
        products.setProductState(ProductState.SALE); // ProductState를 "SALE"로 설정

        List<MultipartFile> imageFiles = request.getProductImages();
        List<String> imageUrls = s3Service.uploadProductImageList(BucketDir.PRODUCT, imageFiles); // 단순한 이미지 url 리스트 출력 -> 수정 필요

        // 첫 번째 이미지를 thumbnail로 설정
        if (!imageUrls.isEmpty()) {
            products.setThumbnail(imageUrls.get(0));
        }

        productRepository.save(products);
        Long newProductID = products.getId();


        // 나머지 이미지들을 ProductImage 엔터티로 생성하여 저장
        for (int i = 0; i < imageUrls.size(); i++) {
            ProductImage productImage = new ProductImage();
            productImage.setProducts(products);  // products가 Products 엔터티의 인스턴스여야 합니다.
            productImage.setImageUrl(imageUrls.get(i));

            // 이미지 이름 설정
            productImage.setImageName(newProductID +"Image#" + (i+1));
            // 이미지 번호 설정
            productImage.setImageNum(i+1);

            // ProductImage 저장
            productImageRepository.save(productImage);
        }

        return products.getId();
    }



    // 게시물 전체 조회





    // 썸네일 업데이트
//    @Transactional
//    public void updateThumbnail(List<ProductImage> images, Long productId) {
//        Products product = productRepository.findById(productId).get();
//        product.setThumbnail(images);
//    }


    // 게시물에서 이미지 저장
//    @Transactional
//    public List<ProductImage> getProductImage(List<MultipartFile> productImages, Products product){
//        return productImages.stream()
//                .map((image) -> {
//                    try {
//                        s3Service.uploadProductImageList(BucketDir.PRODUCT, imageFiles);
//                        return ProductImage.builder()
//                                .imageUrl(url)
//                                .fileName(s3Uploader.getFileName(url))
//                                .product(product)
//                                .build();
//                        return productImageService.save(productImageService.convert(image, product));
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .collect(Collectors.toList());
//    }

//            List<MultipartFile> imageFiles = request.getProductImages();
//            List<String> imageUrls = s3Service.uploadProductImageList(BucketDir.PRODUCT, imageFiles);

}