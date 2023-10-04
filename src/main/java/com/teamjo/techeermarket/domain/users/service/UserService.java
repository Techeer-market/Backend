package com.teamjo.techeermarket.domain.users.service;

import com.teamjo.techeermarket.domain.users.dto.SignUpRequestDto;
import com.teamjo.techeermarket.domain.users.entity.Social;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.mapper.UserMapper;
import com.teamjo.techeermarket.domain.users.repository.UserRepository;
import com.teamjo.techeermarket.global.exception.user.UserNotFoundException;
import com.teamjo.techeermarket.global.jwt.JWTUtill;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtill jwtUtill;

    /*
    //   회원가입 API
    */
    @Transactional
    public void signUp(SignUpRequestDto signUpRequestDto) {

        Users user = userMapper.toEntity(signUpRequestDto);
        String email = signUpRequestDto.getEmail();          // 이메일 중복 여부 확인
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        // social 필드가 null이면 LOCAL로 설정
        if(user.getSocial() == null) {
            user.setSocial(Social.LOCAl);
        }

        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        userRepository.save(user);
    }


    /*
    //  로그인 API
    */
    public ResponseEntity<?> login(String email, String password, HttpServletResponse response) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException());

        if (passwordEncoder.matches(password, user.getPassword())) {
            String accessToken = jwtUtill.makeAccessToken(user);
            String refreshToken = jwtUtill.makeRefreshToken(user);  // 리프레시 토큰 생성

            Cookie accessTokenCookie = new Cookie("access_token", accessToken);
            Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);  // 리프레시 토큰 쿠키

            response.addCookie(accessTokenCookie);
            response.addCookie(refreshTokenCookie);  // 리프레시 토큰 추가

            return ResponseEntity.ok(user);  // 필요에 따라 user 정보 또는 다른 정보를 반환
        } else {
            throw new BadCredentialsException("Invalid password");
        }
    }



}
