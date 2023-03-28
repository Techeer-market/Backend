package com.teamjo.techeermarket.domain.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocialLoginController {
    @GetMapping("/login/oauth2/code/kakao")
    public String kakaoLogin(String code) {
        return code;
    }

    @GetMapping("/login/oauth2/code/google")
    public String GoogleLogin(String code) {
        return code;
    }

    @GetMapping("/login/oauth2/code/naver")
    public String NaverLogin(String code) {
        return code;
    }
}
