package com.teamjo.techeermarket.domain.users.controller;

import com.teamjo.techeermarket.domain.users.dto.request.UsersRequestDto;
import com.teamjo.techeermarket.domain.users.dto.response.UsersResponseDto;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.mapper.UsersMapper;
import com.teamjo.techeermarket.domain.users.service.UsersApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

//    @PostMapping("/signup")
//    public UsersResponseDto update(@ModelAttribute UsersRequestDto usersRequestDto) {
//        log.info(usersRequestDto.toString());
//        if (usersRequestDto == null) {
//            return null;
//        }
//        Users users = usersApiService.signup(usersRequestDto);
//        return usersMapper.fromEntity(users);
//    }
}
