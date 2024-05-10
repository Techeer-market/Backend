package com.teamjo.techeermarket.domain.users.controller;

import com.teamjo.techeermarket.domain.chats.dto.response.ProductInfo;
import com.teamjo.techeermarket.domain.users.dto.SignUpRequestDto;
import com.teamjo.techeermarket.domain.users.dto.UserChangeInfoDto;
import com.teamjo.techeermarket.domain.users.dto.UserDetailResponseDto;
import com.teamjo.techeermarket.domain.users.dto.UserIdDto;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.service.UserService;
import com.teamjo.techeermarket.domain.users.service.UserServiceImpl;
import com.teamjo.techeermarket.global.config.UserDetailsImpl;
import com.teamjo.techeermarket.global.exception.user.InvalidRefreshTokenException;
import com.teamjo.techeermarket.global.jwt.JwtUtill;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtill jwtUtill;

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
    @GetMapping
    public ResponseEntity<UserDetailResponseDto> getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return ResponseEntity.ok(userService.getUserInfo(userDetailsImpl.getUsername()));
    }



    /*
    //  유저 정보 수정
    */
    @PatchMapping("/update")
    public ResponseEntity<UserDetailResponseDto> updateUserInformation(@RequestBody UserChangeInfoDto changeInfoDto,
                                                                       @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        String userEmail = userDetailsImpl.getUsername();
        UserDetailResponseDto updatedUserInfo = userService.updateUserInfo(userEmail, changeInfoDto);
        return ResponseEntity.ok(updatedUserInfo);
    }



    /*
    //  유저 id 조회 - 채팅에서 사용
    */
    @GetMapping("/id")
    public ResponseEntity<UserIdDto> getUserId(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {

        UserIdDto userIdDto = UserIdDto.builder()
                .userId(userDetailsImpl.getUser().getId())
                .build();

        return ResponseEntity.ok(userIdDto);
    }



    /*
    //  Refresh 토큰 API
    //  refresh 토큰이 유효하면 -> access token만 재발급
    //  refresh 토큰이 유효하지 않으면 -> 로그아웃 처리
    */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(HttpServletRequest request) {
        String refreshTokenHeader = request.getHeader("Refresh-Token");
        if (refreshTokenHeader == null || !refreshTokenHeader.startsWith("refresh_token:")) {
            throw new InvalidRefreshTokenException();
        }
        String refreshToken = refreshTokenHeader.substring("refresh_token:".length()).trim();
        Map<String, String> tokens = userService.makerefreshTokens(refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Token", "access_token:" + tokens.get("accessToken"));
        headers.add("Refresh-Token", "refresh_token:" + tokens.get("refreshToken"));
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }




}
