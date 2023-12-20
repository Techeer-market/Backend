package com.teamjo.techeermarket.domain.products.service;

import com.teamjo.techeermarket.domain.images.repository.ProductImageRepository;
import com.teamjo.techeermarket.domain.images.service.ProductImageService;
import com.teamjo.techeermarket.domain.mypage.entity.UserLike;
import com.teamjo.techeermarket.domain.mypage.entity.UserPurchase;
import com.teamjo.techeermarket.domain.mypage.repository.UserLikeRepository;
import com.teamjo.techeermarket.domain.mypage.repository.UserPurchaseRepository;
import com.teamjo.techeermarket.domain.products.entity.ProductState;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.products.mapper.ProductMapper;
import com.teamjo.techeermarket.domain.products.repository.CategoryRepository;
import com.teamjo.techeermarket.domain.products.repository.ProductRepository;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.repository.UserRepository;
import com.teamjo.techeermarket.global.S3.S3Service;
import com.teamjo.techeermarket.global.exception.product.NotYourProductException;
import com.teamjo.techeermarket.global.exception.product.ProductNotFoundException;
import com.teamjo.techeermarket.global.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ProductSubService  {

    private final ProductRepository productRepository;
    private final ProductImageService productImageService;
    private final UserLikeRepository userLikeRepository;
    private final UserPurchaseRepository userPurchaseRepository;
    private final UserRepository userRepository;
    @Autowired
    private final ProductMapper productMapper;
    @Autowired
    private final S3Service s3Service;
    @Autowired
    private final CategoryRepository categoryRepository;
    @Autowired
    private final ProductImageRepository productImageRepository;


    /*
    // 게시물 작성후 userPurchase table에 추가
    */
    @Transactional
    public void updateProductSeller(String email, Long productId){
        // 유저 정보 가져옴
        Users seller = userRepository.findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        // 상품 정보를 가져옴
        Products products = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
        // UserPurchase 테이블에 저장
        if (!userPurchaseRepository.existsBySellerIdAndProducts(seller, products)){
            UserPurchase userPurchase = new UserPurchase();
            userPurchase.setProducts(products);
            userPurchase.setSellerId(seller);
            userPurchaseRepository.save(userPurchase);
        }
    }



    /*
    // 게시물 조회수 업데이트
     */
    @Transactional
    public void increaseViewCount (Long productId) {
        // 상품을 데이터베이스에서 찾음
        Products product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
        int views = product.getViews();
        product.setViews(views+1);

        productRepository.save(product);
    }


    /*
    // 상품 게시물 상태 변경
     */
    @Transactional
    public void updateProductState(Long productId, ProductState newState, String email) {
        Users findUsers = userRepository.findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        // 상품을 데이터베이스에서 찾음
        Products product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        // 현재 로그인한 사용자의 게시물인지 확인
        if (!product.getUsers().getEmail().equals(email)) {
            throw new NotYourProductException();
        }
        // 상태 업데이트
        product.setProductState(newState);
        productRepository.save(product);
    }


    /*
    // 상품에 좋아요를 누르는 메서드
    */
    public void likeProduct(String email, Long productId) {
        // 유저 정보 가져옴
        Users users = userRepository.findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        // 상품 정보를 가져옴
        Products products = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        // 사용자가 해당 상품에 이미 좋아요를 누른 적이 없으면
        if (!userLikeRepository.existsByUsersAndProducts(users, products)) {
            // 좋아요 정보를 생성하여 저장
            UserLike userLike = new UserLike();
            userLike.setUsers(users);
            userLike.setProducts(products);
            userLikeRepository.save(userLike);
        }
    }



    /*
    // 상품에 좋아요를 취소하는 메서드
     */
    public void unlikeProduct(String email, Long productId) {
        // 유저 정보 가져옴
        Users users = userRepository.findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        // 상품 정보를 가져옴
        Products products = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        // 사용자가 해당 상품에 좋아요를 누른 적이 있다면
        userLikeRepository.findByUsersAndProducts(users, products)
                .ifPresent(userLikeRepository::delete); // 좋아요 정보를 삭제
    }

    /*
    // 게시물 조회수 업데이트
     */
    @Transactional
    public void increaseViewsCount (Long productId) {
        // 상품 정보를 가져옴
        Products products = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        int views = products.getViews();
        products.setViews(views + 1);

        productRepository.save(products);  // 상태 변경 저장
    }





}
