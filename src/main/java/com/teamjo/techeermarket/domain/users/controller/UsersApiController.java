package com.teamjo.techeermarket.domain.users.controller;

import com.teamjo.techeermarket.domain.users.dto.request.UsersLoginRequestDto;
import com.teamjo.techeermarket.domain.users.dto.request.UsersSignupRequestDto;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.mapper.UsersMapper;
import com.teamjo.techeermarket.domain.users.service.UsersApiService;
import com.teamjo.techeermarket.global.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
@Slf4j
public class UsersApiController {

    private final UsersApiService usersApiService;

    private final UsersMapper usersMapper;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@ModelAttribute @Validated UsersSignupRequestDto usersSignupRequestDto, Errors errors) {
        // validation check
        if(errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please check your value");
        }
        // 객체가 null인 경우 에러 코드 404 return
        if (usersSignupRequestDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please enter your information");
        }

        Users users = usersApiService.signup(usersSignupRequestDto);

        // 이메일이 중복될 경우 에러 코드 409 return
        if(users == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The email is duplicated. Please check your email.");
        }
        return ResponseEntity.ok(usersMapper.fromEntity(users));
    }
    @ResponseBody
    @GetMapping("/auth/kakao")
    public void kakaoCallBack(@RequestParam String code){
        log.info("UserApiController :: {}", code);
        usersApiService.getKaKaoAccessToken(code);
    }

    @PostMapping("/login")
    public TokenInfo login(@ModelAttribute @Validated UsersLoginRequestDto usersLoginRequestDto) {
        String memberId = usersLoginRequestDto.getEmail();
        String password = usersLoginRequestDto.getPassword();
        TokenInfo tokenInfo = usersApiService.login(memberId, password);
        return tokenInfo;
    }

    @DeleteMapping("/{userUuid}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userUuid) {
        usersApiService.deleteUser(userUuid);
        return ResponseEntity.ok().build();
    }

}