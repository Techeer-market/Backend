package com.teamjo.techeermarket.domain.products.service;

import com.teamjo.techeermarket.domain.images.entity.ProductImage;
import com.teamjo.techeermarket.domain.images.repository.ProductImageRepository;
import com.teamjo.techeermarket.domain.mypage.repository.UserLikeRepository;
import com.teamjo.techeermarket.domain.mypage.repository.UserPurchaseRepository;
import com.teamjo.techeermarket.domain.products.dto.request.ProductRequestDto;
import com.teamjo.techeermarket.domain.products.dto.request.ProductUpdateRequestDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductDetailViewDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductPreViewDto;
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
import com.teamjo.techeermarket.global.exception.product.CategoryNotFoundException;
import com.teamjo.techeermarket.global.exception.product.FailedToDeleteProductException;
import com.teamjo.techeermarket.global.exception.product.NotYourProductException;
import com.teamjo.techeermarket.global.exception.product.ProductNotFoundException;
import com.teamjo.techeermarket.global.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {


    @Autowired
    private final ProductMapper productMapper;
    @Autowired
    private final S3Service s3Service;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final UserLikeRepository userLikeRepository;
    @Autowired
    private final UserPurchaseRepository userPurchaseRepository;
    @Autowired
    private final ProductImageRepository productImageRepository;


    /**
     * // 상품 저장 ( = 게시물 저장)
     **/
    @Transactional
    public Long saveProduct(ProductRequestDto request, String email) throws IOException {
        Users findUsers = userRepository.findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        Categorys findCategory = categoryRepository.findIdByName(request.getCategoryName());
        if (findCategory == null) {
            throw new CategoryNotFoundException();    // 카테고리 확인
        }

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
            productImage.setImageName(newProductID + "Image#" + (i + 1));
            // 이미지 번호 설정
            productImage.setImageNum(i + 1);
            // ProductImage 저장
            productImageRepository.save(productImage);
        }

        return products.getId();
    }


    /**
     * // 게시물 전체 목록 보기
     **/
    @Transactional(readOnly = true)
    public List<ProductPreViewDto> getAllProductList(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("id").descending());  // 1페이지부터 시작하도록
        Page<Products> productPage = productRepository.findAll(pageable);
        return productPage.stream()
                .map(productMapper::fromListEntity)
                .collect(Collectors.toList());
    }

    /**
     * // 판매완료된 상품 제외 - 게시물 전체 목록 보기
     **/
    @Transactional(readOnly = true)
    public List<ProductPreViewDto> getListExceptSold(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("id").descending());  // 1페이지부터 시작하도록
        Page<Products> productPage = productRepository.findAll(pageable);

        // 판매 완료 된 상품 제외
        List<Products> filteredProducts = productPage.stream()
                .filter(product -> product.getProductState() != ProductState.SOLD)
                .collect(Collectors.toList());

        return filteredProducts.stream()
                .map(productMapper::fromListEntity)
                .collect(Collectors.toList());
    }


    /**
     * // 상품 게시물 상세 조회
     **/
    @Transactional(readOnly = true)
    public ProductDetailViewDto getProductDetail(String email, Long productId) {
        // 유저 정보 가져옴
        Users findUsers = userRepository.findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        // 상품 정보를 가져옴
        Products products = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        return productMapper.fromDetailEntity(products);

    }


    /**
     * //  게시물 삭제하기
     **/
    @Transactional
    public void deleteProduct(Long productId, String userEmail) {
        Users findUsers = userRepository.findUserByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);

        Products products = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        // 자신의 게시물인지 확인
        if (!products.getUsers().equals(findUsers)) {
            throw new NotYourProductException();
        }

        // 트랜젝션 시작
        try {
            // 게시물에 연결된 좋아요 정보 삭제
            userLikeRepository.deleteByProducts(products);
            // 게시물에 연결된 구매 정보 삭제
            userPurchaseRepository.deleteByProducts(products);
            // 게시물에 연결된 이미지들을 s3에서 삭제
            List<ProductImage> productImages = products.getProductImages();
            for (ProductImage productImage : productImages) {
                String imageUrl = productImage.getImageUrl();
                s3Service.deleteImage(imageUrl);
            }

            // 게시물 삭제
            productRepository.delete(products);
        } catch (Exception e) {
            // 오류 생기면 롤백
            throw new FailedToDeleteProductException();
        }
    }



    /**
     //  게시물 검색하기
     **/
    @Transactional(readOnly = true)
    public List<ProductPreViewDto> searchProductsTitle(String search) {
        List<Products> products = productRepository.findByTitleContainingOrderByIdDesc(search);

        // 판매된 상품 제외
        List<Products> filteredProducts = products.stream()
                .filter(product -> product.getProductState() != ProductState.SOLD)
                .collect(Collectors.toList());

        return filteredProducts.stream()
                .map(productMapper::fromListEntity)
                .collect(Collectors.toList());
    }


    /**
     //  게시물 수정하기
     **/
//    @Transactional
//    public Long updateProduct (Long productId, ProductUpdateRequestDto updateRequest, String email) throws IOException {
//        // 유저 정보 가져옴
//        Users findUsers = userRepository.findUserByEmail(email)
//                .orElseThrow(UserNotFoundException::new);
//        // 상품 정보를 가져옴
//        Products existingProduct = productRepository.findById(productId)
//                .orElseThrow(ProductNotFoundException::new);
//        // 현재 사용자가 게시물 작성자인지 확인
//        if (!existingProduct.getUsers().equals(findUsers)) {
//            throw new NotYourProductException();
//        }
//        // 게시물 수정
//        Categorys newCategory = categoryRepository.findIdByName(updateRequest.getCategoryName());
//        if (newCategory == null) {
//            throw new CategoryNotFoundException();
//        }
//
//        // 이미지 수정
//        List<MultipartFile> newImages = updateRequest.getNewProductImages();
//        if (newImages != null && !newImages.isEmpty()) {
//            // 새로운 이미지 s3 업로드
//            List<String> newImageUrls = s3Service.uploadProductImageList(BucketDir.PRODUCT, newImages);
//
//            // 기존 이미지 유지 및 새로운 이미지 추가
//            List<String> updatedImages = updateRequest.getExistProductImages();
//            updatedImages.addAll(newImageUrls);
//
//            // 첫번째(썸네일용) 사진이 바뀌었다면 수정 - 그냥 무조건 1번 사진이 바뀌면 수정을 해야하나..?
//            existingProduct.setThumbnail(updatedImages.get(0));
//
//            // 이전에 사용하지 않는 사진들은 삭제해줘야 하고
//            // s3Service.deleteImage(imageUrl);
//
//            // 새로 오는 jpg 사진과 url 사진을 순서를 어떻게 알수 있는지 잘 모르겠다.
//
//            // 새로운 이미지들을 ProductImage 엔터티로 생성하여 저장
//            for (int i = 0; i < newImageUrls.size(); i++) {
//                ProductImage productImage = new ProductImage();
//                productImage.setProducts(existingProduct);  // products가 Products 엔터티의 인스턴스
//                productImage.setImageUrl(newImageUrls.get(i));
//                // 이미지 이름 설정
//                productImage.setImageName(productId +"Image#" + (i+1));  // 그냥 +1 로 하는게 아니라 자신의 번호에 맞춰서 저장되야 할거같다.
//                // 이미지 번호 설정
//                productImage.setImageNum(i+1);  // 얘도 자신의 번호에 맞게 어떻게 해야 할지 모르겠다
//                // ProductImage 저장
//                productImageRepository.save(productImage);
//            }
//        }
//
//        // 수정된 내용 저장
//        productMapper.updateProductFromDto(existingProduct, updateRequest, newCategory);
//        productRepository.save(existingProduct);
//
//        // 상품 게시물 id 값 반환
//        return existingProduct.getId();
//    }







}
