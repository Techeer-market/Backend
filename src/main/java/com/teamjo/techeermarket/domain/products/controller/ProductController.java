package com.teamjo.techeermarket.domain.products.controller;

import com.teamjo.techeermarket.domain.products.dto.request.ProductRequestDto;
import com.teamjo.techeermarket.domain.products.entity.ProductState;
import com.teamjo.techeermarket.domain.products.service.ProductService;
import com.teamjo.techeermarket.domain.products.service.ProductSubService;
import com.teamjo.techeermarket.global.config.UserDetailsImpl;
import com.teamjo.techeermarket.global.exception.product.InvalidProductStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

import static com.teamjo.techeermarket.global.exception.ErrorCode.INVALID_PRODUCT_STATE;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductSubService productSubService;

    @PostMapping
    public HttpStatus saveProduct (@Validated @ModelAttribute ProductRequestDto productRequstDto,
                                   @AuthenticationPrincipal UserDetailsImpl userDetailsImpl ) throws IOException {
        String email = userDetailsImpl.getUsername();
        // 상품 DB에 저장
        Long productId = productService.saveProduct(productRequstDto, email);

        // UserPurchase DB에 저장
        // 상품 id + userid(seller-id) 만 저장

        // 상품 상세 조회로 redirect
//        redirectAttributes.addAttribute("productId", productId);
//        return "redirect:/products/list/{productId}";

        return HttpStatus.OK;
    }


    /*
    // 상품 게시물 상태 변경
     */
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


}