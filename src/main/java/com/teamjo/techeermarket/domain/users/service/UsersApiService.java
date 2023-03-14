package com.teamjo.techeermarket.domain.users.service;

import com.teamjo.techeermarket.domain.users.dto.request.UsersRequestDto;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.mapper.UsersMapper;
import com.teamjo.techeermarket.domain.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UsersApiService {

    public final UsersRepository usersRepository;

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

        return usersRepository.save(usersMapper.toEntity(usersRequestDto));
    }

}
