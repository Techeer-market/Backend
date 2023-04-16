package com.teamjo.techeermarket.domain.users.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SocialLoginController {

    @ResponseBody
    @GetMapping("/login/oauth2/code/kakao")
    public String kakaoLogin(@RequestParam String code) {
        log.info(code);
        return code;
    }

    @ResponseBody
    @GetMapping("/login/oauth2/code/google")
    public String GoogleLogin(@RequestParam String code) {
        log.info(code);
        return code;
    }

    @ResponseBody
    @GetMapping("/login/oauth2/code/naver")
    public String NaverLogin(@RequestParam String code) {
        log.info(code);
        return code;
    }
}
