package com.teamjo.techeermarket.domain.users.service;

import com.teamjo.techeermarket.domain.users.dto.SignUpRequestDto;
import com.teamjo.techeermarket.domain.users.dto.UserChangeInfoDto;
import com.teamjo.techeermarket.domain.users.dto.UserDetailResponseDto;
import com.teamjo.techeermarket.domain.users.entity.Users;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    void signUp(SignUpRequestDto signUpRequestDto);

    @Transactional(readOnly = true)
    UserDetailResponseDto getUserInfo(String email);

    UserDetailResponseDto updateUserInfo(String currentEmail, UserChangeInfoDto changeInfoDto);

    Users findUser(String email);

}
