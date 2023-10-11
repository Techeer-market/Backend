package com.teamjo.techeermarket.domain.users.service;

import com.teamjo.techeermarket.domain.users.dto.SignUpRequestDto;
import com.teamjo.techeermarket.domain.users.dto.UserDetailResponseDto;
import com.teamjo.techeermarket.domain.users.entity.Social;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.mapper.UserMapper;
import com.teamjo.techeermarket.domain.users.repository.UserRepository;
import com.teamjo.techeermarket.global.exception.user.InvalidTokenException;
import com.teamjo.techeermarket.global.exception.user.UserEmailAlreadyExistsException;
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
import java.util.HashMap;
import java.util.Map;

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
            throw new UserEmailAlreadyExistsException();
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


    /*
    //  Refresh 토큰 API
    //  refresh 토큰이 유효하면 -> access token만 재발급
    //  refresh 토큰이 유효하지 않으면 + 10일 이내로 남으면 -> RT,AT 둘다 재발급
    */
//    public Map<String, String> refreshToken(String refreshToken) {
//        if (jwtUtill.validateToken(refreshToken)) {
//            String email = jwtUtill.getEmailFromToken(refreshToken);
//            Users user = userRepository.findByEmail(email)
//                    .orElseThrow(() -> new UserNotFoundException());
//
//            String newAccessToken = jwtUtill.makeAccessToken(user);
//
//            Map<String, String> tokens = new HashMap<>();
//            tokens.put("access_token", newAccessToken);
//
//            if (jwtUtill.getRemainingDays(refreshToken) < 10) {
//                String newRefreshToken = jwtUtill.makeRefreshToken(user);
//                tokens.put("refresh_token", newRefreshToken);
//            }
//
//            return tokens;
//        } else {
//            throw new InvalidTokenException();
//        }
//    }


    /*
    //  유저 정보 조회
    */
    public UserDetailResponseDto getUser(String email) {
        Users user = userRepository.findByEmail(email)
              .orElseThrow(() -> new UserNotFoundException());

        return userMapper.fromEntity(user);
    }


}
