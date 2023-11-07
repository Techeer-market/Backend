package com.teamjo.techeermarket.domain.products.controller;

import com.teamjo.techeermarket.domain.products.dto.request.ProductRequestDto;
import com.teamjo.techeermarket.domain.products.service.ProductService;
import com.teamjo.techeermarket.global.config.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public HttpStatus saveProduct (@Validated @ModelAttribute ProductRequestDto productRequstDto,
                                   @AuthenticationPrincipal UserDetailsImpl userDetailsImpl ) {
        String email = userDetailsImpl.getUsername();
        // 상품 DB에 저장
        Long productId = productService.saveProduct(productRequstDto, email);

        // 상품 상세 조회로 redirect
        return HttpStatus.OK;
    }



}