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
import com.teamjo.techeermarket.socket.repository.ChatRoomRepository;
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
    private final ChatRoomRepository chatRoomRepository ;
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
        setProductImg(products, imageUrls, newProductID);

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

        // 판매 완료된 상품 제외한 목록을 페이징된 상태로 가져오기
        Page<Products> productPage = productRepository.findByProductStateNot(ProductState.SOLD, pageable);

        return productPage.stream()
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

        // 내가 좋아요 눌렀는지 유무
        boolean myheart = userLikeRepository.existsByUsersAndProducts(findUsers, products);

        ProductDetailViewDto productDetailViewDto = productMapper.fromDetailEntity(products);
        productDetailViewDto.setMyheart(myheart);

        return productDetailViewDto;
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
            // 게시물에 연결된 채팅방 삭제
            chatRoomRepository.deleteByProducts(products);
            /**
             * 추후에 채팅 유저, 채팅 까지 삭제해야할지 고려하기
             */


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
    public Page<ProductPreViewDto> searchProductsTitle(int pageNo, int pageSize, String search) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("id").descending());  // 1페이지부터 시작하도록

        return productRepository.findAllByTitleContainingOrderByIdDesc(search, pageable)
                .map(productMapper::fromListEntity);
    }



    /**
     //  게시물 수정하기
     **/
    @Transactional
    public Long updateProduct (Long productId, ProductRequestDto updateRequest, String email) throws IOException {
        // 유저 정보 가져옴
        Users findUsers = userRepository.findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        // 상품 정보를 가져옴
        Products existingProduct = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
        // 현재 사용자가 게시물 작성자인지 확인
        if (!existingProduct.getUsers().equals(findUsers)) {
            throw new NotYourProductException();
        }
        // 게시물 수정
        Categorys newCategory = categoryRepository.findIdByName(updateRequest.getCategoryName());
        if (newCategory == null) {
            throw new CategoryNotFoundException();
        }

        // 이미지 삭제
        // 게시물에 연결된 이전 이미지들을 s3에서 삭제
        List<ProductImage> productImages = existingProduct.getProductImages();
        for (ProductImage productImage : productImages) {
            String imageUrl = productImage.getImageUrl();
            s3Service.deleteImage(imageUrl);
        }
        // 게시물에 연결된 이전 이미지들 db에서 삭제
         existingProduct.getProductImages().clear();


        // 수정된 내용 저장
        productMapper.updateProductFromDto(existingProduct, updateRequest, newCategory);

        // 이미지 업로드 및 저장
        List<MultipartFile> imageFiles = updateRequest.getProductImages();
        List<String> imageUrls = s3Service.uploadProductImageList(BucketDir.PRODUCT, imageFiles);
        // 첫 번째 이미지를 thumbnail로 수정
        if (!imageUrls.isEmpty()) {
            existingProduct.setThumbnail(imageUrls.get(0));}

        // 새로운 이미지들을 ProductImage 엔터티로 생성하여 저장
        setProductImg(existingProduct, imageUrls, productId);

        // 수정된 내용 저장
        productRepository.save(existingProduct);

        // 상품 게시물 id 값 반환
        return existingProduct.getId();
    }




    /**
        새로운 이미지 ProductImage 엔티티로 생성하여 저장하는 메소드
     */
    private void setProductImg(Products products, List<String> imageUrls, Long newProductID) {
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
    }





}
