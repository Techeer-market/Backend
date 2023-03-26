package com.teamjo.techeermarket.domain.users.service;

import com.teamjo.techeermarket.domain.users.dto.request.UsersRequestDto;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.mapper.UsersMapper;
import com.teamjo.techeermarket.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UsersApiService {

    public final UserRepository userRepository;

    public final UsersMapper usersMapper;

    public final PasswordEncoder passwordEncoder;

    @Transactional
    public Users signup(UsersRequestDto usersRequestDto) {
        try {
            String password = passwordEncoder.encode(usersRequestDto.getPassword());
            usersRequestDto.setPassword(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
<<<<<<< refs/remotes/origin/develop
        return userRepository.save(usersMapper.toEntity(usersRequestDto));
    }
    //유저 정보 세션 처리 예정
    @Transactional
    public Users update(Users updateUser){
        Users existingUser = userRepository.findById(updateUser.getId())
                .orElseThrow(()-> new RuntimeException("유저 정보를 찾을 수 없습니다."));
        existingUser.update(updateUser);
        return userRepository.save(existingUser);
=======

        return userRepository.save(usersMapper.toEntity(usersRequestDto));
>>>>>>> (TM-10) 카카오,구글,네이버 로그인
    }
    //유저 정보 세션 처리 예정
    @Transactional
    public Users delete(Users deleteUser){
        Users existingUser = userRepository.findById(deleteUser.getId())
                .orElseThrow(()-> new RuntimeException("유저 정보를 찾알 수 없습니다."));
        existingUser.setDeleted(true);
//        existingUser.delete(deleteUser);
        return userRepository.save(existingUser);
    }




}
