package com.teamjo.techeermarket.domain.users.controller;

import com.teamjo.techeermarket.domain.users.dto.request.UsersLoginRequestDto;
import com.teamjo.techeermarket.domain.users.dto.request.UsersRequestDto;
import com.teamjo.techeermarket.domain.users.dto.response.UsersResponseDto;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.mapper.UsersMapper;
import com.teamjo.techeermarket.domain.users.service.UsersApiService;
//import com.teamjo.techeermarket.global.security.JwtToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
@Slf4j
public class UsersApiController {

    private final UsersApiService usersApiService;

    private final UsersMapper usersMapper;

    @PostMapping("/signup")
    public UsersResponseDto signup(@ModelAttribute UsersRequestDto usersRequestDto) {
        log.info(usersRequestDto.toString());
        if (usersRequestDto == null) {
            return null;
        }
        Users users = usersApiService.signup(usersRequestDto);
        return usersMapper.fromEntity(users);
    }

//    @PostMapping("/login")
//    public ResponseEntity<JwtToken> loginSuccess(@ModelAttribute UsersLoginRequestDto loginForm) {
//        log.info(loginForm.toString());
//        JwtToken token = usersApiService.login(loginForm.getEmail(), loginForm.getPassword());
//        return ResponseEntity.ok(token);
//    }

}
