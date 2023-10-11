package com.teamjo.techeermarket.domain.users.controller;

import com.teamjo.techeermarket.domain.users.dto.LoginRequestDto;
import com.teamjo.techeermarket.domain.users.dto.SignUpRequestDto;
import com.teamjo.techeermarket.domain.users.dto.UserDetailResponseDto;
import com.teamjo.techeermarket.domain.users.service.UserService;
import com.teamjo.techeermarket.global.config.CustomUserDetailsImpl;
import com.teamjo.techeermarket.global.exception.user.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    /*
    //  회원가입 API
    */
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        userService.signUp(signUpRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    /*
    //  로그인 API
    */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword(), response);
    }



    /*
    //  Refresh 토큰 API
    //  refresh 토큰이 유효하면 -> access token만 재발급
    //  refresh 토큰이 유효하지 않으면 + 10일 이내로 남으면 -> RT,AT 둘다 재발급
    */
//    @PostMapping("/refresh")
//    public ResponseEntity<Map<String, String>> refresh(@RequestParam String refreshToken, HttpServletResponse response) {
//        try {
//            Map<String, String> newTokens = userService.refreshToken(refreshToken);
//
//            Cookie newAccessTokenCookie = new Cookie("access_token", newTokens.get("access_token"));
//            response.addCookie(newAccessTokenCookie);
//
//            if (newTokens.containsKey("refresh_token")) {
//                Cookie newRefreshTokenCookie = new Cookie("refresh_token", newTokens.get("refresh_token"));
//                response.addCookie(newRefreshTokenCookie);
//            }
//
//            return ResponseEntity.ok(newTokens);
//        } catch (InvalidTokenException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//    }



    /*
    //  유저 정보 조회
    */
    @GetMapping("/")
    public ResponseEntity<UserDetailResponseDto> getUser(@AuthenticationPrincipal CustomUserDetailsImpl customUserDetailsImpl) {
        System.out.println("Email: " + customUserDetailsImpl.getUsername());  // 로그 추가
        return ResponseEntity.ok(userService.getUser(customUserDetailsImpl.getUsername()));
    }



}
