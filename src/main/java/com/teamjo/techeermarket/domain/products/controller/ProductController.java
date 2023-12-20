package com.teamjo.techeermarket.domain.products.controller;

import com.teamjo.techeermarket.domain.mypage.service.MyPageService;
import com.teamjo.techeermarket.domain.products.dto.request.ProductRequestDto;
import com.teamjo.techeermarket.domain.products.dto.request.ProductUpdateRequestDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductDetailViewDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductPreViewDto;
import com.teamjo.techeermarket.domain.products.entity.ProductState;
import com.teamjo.techeermarket.domain.products.service.ProductService;
import com.teamjo.techeermarket.domain.products.service.ProductSubService;
import com.teamjo.techeermarket.global.config.UserDetailsImpl;
import com.teamjo.techeermarket.global.exception.product.InvalidProductStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductSubService productSubService;

    private final MyPageService myPageService;

    /**
    // 게시물 작성하기
     **/
    @PostMapping
    public ResponseEntity<?> saveProduct (@Validated @ModelAttribute ProductRequestDto productRequstDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetailsImpl ) throws IOException {
        String email = userDetailsImpl.getUsername();
        // 상품 DB에 저장
        Long productId = productService.saveProduct(productRequstDto, email);

        // UserPurchase DB에 => 상품 id + (seller-id) 만 저장
        productSubService.updateProductSeller(email,productId);

        // 상품 ID 리턴
        return ResponseEntity.ok(Map.of("productId", productId));
    }



    /**
    // 상품 게시물 상태 변경
     **/
    @PutMapping("/state/{productId}")
    public ResponseEntity<String> updateProductState(
            @PathVariable Long productId,
            @RequestBody Map<String, String> requestBody,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        String email = userDetailsImpl.getUsername();
        // 요청 바디에서 state 값을 가져옴
        String state = requestBody.get("state");
        // 상태가 올바른지 확인
        try{
            ProductState productState = ProductState.valueOf(state);
        } catch (IllegalArgumentException e) {
            throw new InvalidProductStateException() ; }

        // 서비스를 통해 상태 변경
        productSubService.updateProductState(productId, ProductState.valueOf(state),email);
        return ResponseEntity.status(HttpStatus.OK).body("Product state updated successfully");
    }



    /**
    //  게시물 전체 목록 보기
     **/
    @GetMapping("/list")
    public ResponseEntity<List<ProductPreViewDto>> getAllProductListByPagination(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        List<ProductPreViewDto> productsList = productService.getAllProductList(pageNo, pageSize);
        return ResponseEntity.ok(productsList);
    }



    /**
     //   판매완료된 상품 제외 - 게시물 전체 목록 보기
     **/
    @GetMapping("/list/sell")
    public ResponseEntity<List<ProductPreViewDto>> getListExceptSold(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        List<ProductPreViewDto> productsList = productService.getListExceptSold(pageNo, pageSize);
        return ResponseEntity.ok(productsList);
    }



    /**
     //  상품 게시물 상세 조회
     **/
    @GetMapping("/list/{productId}")
    public ResponseEntity<ProductDetailViewDto> getProductDetail (@PathVariable Long productId,
                                                                  @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        String email = userDetailsImpl.getUsername();
        ProductDetailViewDto productDetailDto = productService.getProductDetail(email, productId);

        // 조회수 증가
        productSubService.increaseViewsCount(productId);

        return ResponseEntity.ok(productDetailDto);
    }



    /**
    // 게시물 삭제하기
     **/
    @DeleteMapping("/{productId}")
    public HttpStatus deleteProduct(@PathVariable Long productId,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String email = userDetails.getUsername();
        productService.deleteProduct(productId, email);

        return HttpStatus.OK ;
    }


    /**
    // 게시물 수정하기
     **/
//    @PutMapping("/{productId}")
//    public ResponseEntity<?> updateProduct(@PathVariable Long productId,
//                                           @Validated @ModelAttribute ProductUpdateRequestDto updateRequest,
//                                           @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
//        String email = userDetails.getUsername();
//        productService.updateProduct(productId, updateRequest, email);
//
//        // 상품 ID 리턴
//        return ResponseEntity.ok(Map.of("productId", productId));
//    }



    /**
    //  게시물 좋아요 누르기
    **/
    @PostMapping("/like/{productId}")
    public HttpStatus likeProduct (@PathVariable Long productId,
                                   @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws IOException {
        String email = userDetailsImpl.getUsername();
        myPageService.likeProduct(email, productId);
        return HttpStatus.OK;
    }



    /**
    //  게시물 좋아요 취소 누르기
    **/
    @DeleteMapping("/like/{productId}")
    public HttpStatus unlikeProduct (@PathVariable Long productId,
                                     @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws IOException {
        String email = userDetailsImpl.getUsername();
        myPageService.unlikeProduct(email, productId);
        return HttpStatus.OK;
    }



    /**
     // 게시물 제목으로 검색하기
     **/
    @GetMapping
    public ResponseEntity<List<ProductPreViewDto>> searchProductsTitle(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam String search) {
        Page<ProductPreViewDto> productPage = productService.searchProductsTitle(pageNo, pageSize, search);

        List<ProductPreViewDto> productList = productPage.getContent();
        return ResponseEntity.ok(productList);
    }



}