package com.teamjo.techeermarket.domain.users.controller;

import com.teamjo.techeermarket.domain.users.dto.LoginRequestDto;
import com.teamjo.techeermarket.domain.users.dto.SignUpRequestDto;
import com.teamjo.techeermarket.domain.users.dto.UserChangeInfoDto;
import com.teamjo.techeermarket.domain.users.dto.UserDetailResponseDto;
import com.teamjo.techeermarket.domain.users.service.UserService;
import com.teamjo.techeermarket.global.config.UserDetailsImpl;
import com.teamjo.techeermarket.global.exception.user.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    /*
    //  test API
    */
    @GetMapping("/test")
    public ResponseEntity<String> privateEndpoint(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        System.out.println("이메일 출력 = " + userDetailsImpl.getUsername());
        String user = userDetailsImpl.getUsername();
        return ResponseEntity.ok(user);
    }


    /*
    //  회원가입 API
    */
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        userService.signUp(signUpRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /*
    //  유저 정보 조회
    */
    @GetMapping("/")
    public ResponseEntity<UserDetailResponseDto> getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        System.out.println("Email: " + userDetailsImpl.getUsername());  // 로그 추가
        return ResponseEntity.ok(userService.getUserInfo(userDetailsImpl.getUsername()));
    }


    /*
    //  유저 정보 수정
    */
    @PatchMapping
    public ResponseEntity<UserDetailResponseDto> updateUserInformation(@RequestBody UserChangeInfoDto changeInfoDto,
                                                                       @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        String userEmail = userDetailsImpl.getUsername();
        UserDetailResponseDto updatedUserInfo = userService.updateUserInfo(userEmail, changeInfoDto);
        return ResponseEntity.ok(updatedUserInfo);
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







}
