package com.teamjo.techeermarket.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    // Common
    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "U001", "사용자를 찾을 수 없습니다."),
    INVALIED_TOKEN_EXCEPTION (HttpStatus.UNAUTHORIZED, "U002", "토큰이 유효하지 않습니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "U003", "이미 존재하는 이메일입니다."),
    REFRESH_TOKEN_IS_INVALID(HttpStatus.BAD_REQUEST, "U004","리프레시 토큰이 유효하지 않습니다.");


    private HttpStatus status;
    private String code;
    private String message;


    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
