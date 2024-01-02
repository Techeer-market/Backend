package com.teamjo.techeermarket.domain.mypage.service;

import com.teamjo.techeermarket.domain.mypage.entity.UserLike;
import com.teamjo.techeermarket.domain.mypage.entity.UserPurchase;
import com.teamjo.techeermarket.domain.mypage.repository.UserLikeRepository;
import com.teamjo.techeermarket.domain.mypage.repository.UserPurchaseRepository;
import com.teamjo.techeermarket.domain.products.dto.response.ProductPreViewDto;
import com.teamjo.techeermarket.domain.products.entity.ProductState;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.products.mapper.ProductMapper;
import com.teamjo.techeermarket.domain.products.repository.ProductRepository;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.repository.UserRepository;
import com.teamjo.techeermarket.domain.users.service.UserService;
import com.teamjo.techeermarket.global.exception.product.InvalidProductRequestException;
import com.teamjo.techeermarket.global.exception.product.InvalidProductStateException;
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

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserLikeRepository userLikeRepository;
    private final UserPurchaseRepository userPurchaseRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    /**
    // 상품에 좋아요를 누르는 메서드
    */
    @Transactional
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

            // 게시물 db - 좋아요수 증가
            productRepository.incrementHeartCount(products);
        } else {
            throw new InvalidProductRequestException();  // 이미 좋아요를 누른 게시물일 경우
        }
    }



    /**
    // 상품에 좋아요를 취소하는 메서드
     */
    @Transactional
    public void unlikeProduct(String email, Long productId) {
        // 유저 정보 가져옴
        Users users = userRepository.findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        // 상품 정보를 가져옴
        Products products = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        // 사용자가 해당 상품에 이미 좋아요를 누른 적이 없으면
        if (!userLikeRepository.existsByUsersAndProducts(users, products)) {
            throw new InvalidProductRequestException();
        }

        // 사용자가 해당 상품에 좋아요를 누른 적이 있다면
        userLikeRepository.findByUsersAndProducts(users, products)
                .ifPresent(userLikeRepository::delete); // 좋아요 정보를 삭제

        // 게시물 db - 좋아요 수를 감소
        productRepository.decrementHeartCount(products);
    }



    /**
    //  내가 좋아요 누른 목록 리스트 보기
     */
    @Transactional(readOnly = true)
    public List<ProductPreViewDto> getLikedProducts (String email, int pageNo, int pageSize) {
        Users findUsers = userRepository.findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        // 사용자가 좋아요 누른 상품 목록 가져오기
        Set<Long> productIds = userLikeRepository.findByUsers(findUsers).stream()
                .map(userLike -> userLike.getProducts().getId())
                .collect(Collectors.toSet());

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("id").descending());  // 1페이지부터 시작하도록

        // 좋아요 누른 상품의 ID 목록을 이용해 페이지네이션된 상품 목록 가져오기
        Page<Products> productPage = productRepository.findByIdIn(productIds, pageable);

        // 페이지에 포함된 상품 목록을 Dto로 변환하여 반환
        return productPage.getContent().stream()
                .map(productMapper::fromListEntity)
                .collect(Collectors.toList());
    }



    /**
    //  내가 구매한 상품 목록 조회
     */
    @Transactional(readOnly = true)
    public List<ProductPreViewDto> getMyPurchasedProducts(String email, int pageNo, int pageSize) {
        Users findUsers = userRepository.findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        // 페이징 처리를 위해 Pageable 객체 생성
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("id").descending());

        // 내가 구매한 상품 목록 조회
        Page<UserPurchase> purchasePage = userPurchaseRepository.findByBuyerId(findUsers, pageable);

        // 페이지에 포함된 상품 목록을 Dto로 변환하여 반환
        return purchasePage.getContent().stream()
                .map(userPurchase -> productMapper.fromListEntity(userPurchase.getProducts()))
                .collect(Collectors.toList());
    }



    /**
     * 나의 판매 내역 조회
     */
    @Transactional(readOnly = true)
    public Map<String, List<ProductPreViewDto>> getSellProducts(String email, int pageNo, int pageSize) {
        Users findUsers = userRepository.findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        // 페이징 처리를 위해 Pageable 객체 생성
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("id").descending());

        // 판매 가능한 상품 목록 조회 (SALE, RESERVED)
        List<ProductPreViewDto> sellableProducts = getProductsByState(findUsers, Arrays.asList(ProductState.SALE, ProductState.RESERVED), pageable);

        // 거래 완료된 상품 목록 조회 (SOLD)
        List<ProductPreViewDto> soldProducts = getProductsByState(findUsers, Collections.singletonList(ProductState.SOLD), pageable);

        // 결과를 Map으로 묶어 반환
        Map<String, List<ProductPreViewDto>> result = new HashMap<>();
        result.put("판매중", sellableProducts);
        result.put("거래완료", soldProducts);

        return result;
    }


    // 특정 상태의 상품 목록 조회 메서드
    private List<ProductPreViewDto> getProductsByState(Users findUsers, List<ProductState> states, Pageable pageable) {
        return productRepository
                .findByUsersAndProductStateIn(findUsers, states, pageable)
                .stream()
                .map(product -> productMapper.fromListEntity((Products) product))
                .collect(Collectors.toList());
    }








}