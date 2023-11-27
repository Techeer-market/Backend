package com.teamjo.techeermarket.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    // 유저
    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "U001", "사용자를 찾을 수 없습니다."),
    INVALID_TOKEN_EXCEPTION (HttpStatus.UNAUTHORIZED, "U002", "토큰이 유효하지 않습니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "U003", "이미 존재하는 이메일입니다."),
    THERE_IS_NO_TOKEN (HttpStatus.BAD_REQUEST, "U004", "입력된 토큰이 없습니다."),

    REFRESH_TOKEN_IS_INVALID(HttpStatus.UNAUTHORIZED, "U005","리프레시 토큰이 유효하지 않습니다."),

    PRODUCT_IS_NOT_YOURS(HttpStatus.UNAUTHORIZED, "U006","해당 상품이 사용자의 것이 아닙니다."),

    // 상품 관련
    PRODUCT_NOT_FOUND (HttpStatus.NOT_FOUND, "P001", "상품을 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "P002", "카테고리를 찾을 수 없습니다."),
    INVALID_PRODUCT_STATE (HttpStatus.BAD_REQUEST, "P003", "상품 상태 정보가 올바르지 않습니다."),
    INVALID_PRODUCT_REQUEST (HttpStatus.BAD_REQUEST, "P004","상품페이지의 올바르지 못한 요청입니다."),

    // 이미지 관련
    IMAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "I001", "유효하지 않은 이미지입니다.");




    private HttpStatus status;
    private String code;
    private String message;


    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
