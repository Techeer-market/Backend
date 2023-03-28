package com.teamjo.techeermarket.domain.users.service;

import com.teamjo.techeermarket.domain.users.dto.request.UsersRequestDto;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.mapper.UsersMapper;
import com.teamjo.techeermarket.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

        return userRepository.save(usersMapper.toEntity(usersRequestDto));
    }



}
