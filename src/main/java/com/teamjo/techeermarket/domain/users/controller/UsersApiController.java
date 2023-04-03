package com.teamjo.techeermarket.domain.users.controller;

import com.amazonaws.Response;
import com.teamjo.techeermarket.domain.users.dto.request.UsersLoginRequestDto;
import com.teamjo.techeermarket.domain.users.dto.request.UsersRequestDto;
import com.teamjo.techeermarket.domain.users.dto.response.UsersResponseDto;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.mapper.UsersMapper;
import com.teamjo.techeermarket.domain.users.service.UsersApiService;
//import com.teamjo.techeermarket.global.security.JwtToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
@Slf4j
public class UsersApiController {

    private final UsersApiService usersApiService;

    private final UsersMapper usersMapper;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@ModelAttribute @Validated UsersRequestDto usersRequestDto, Errors errors) {
        // validation check
        if(errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please check your value");
        }
        // 객체가 null인 경우 에러 코드 404 return
        if (usersRequestDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please enter your information");
        }

        Users users = usersApiService.signup(usersRequestDto);

        // 이메일이 중복될 경우 에러 코드 409 return
        if(users == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The email is duplicated. Please check your email.");
        }
        return ResponseEntity.ok(usersMapper.fromEntity(users));
    }

//    @PostMapping("/login")
//    public ResponseEntity<JwtToken> loginSuccess(@ModelAttribute UsersLoginRequestDto loginForm) {
//        log.info(loginForm.toString());
//        JwtToken token = usersApiService.login(loginForm.getEmail(), loginForm.getPassword());
//        return ResponseEntity.ok(token);
//    }

}
