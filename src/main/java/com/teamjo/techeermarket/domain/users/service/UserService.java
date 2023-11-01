package com.teamjo.techeermarket.domain.users.service;

import com.teamjo.techeermarket.domain.users.dto.SignUpRequestDto;
import com.teamjo.techeermarket.domain.users.dto.UserDetailResponseDto;
import com.teamjo.techeermarket.domain.users.entity.Social;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.mapper.UserFromMapper;
import com.teamjo.techeermarket.domain.users.mapper.UserMapper;
import com.teamjo.techeermarket.domain.users.repository.UserRepository;
import com.teamjo.techeermarket.global.config.UserDetailsImpl;
import com.teamjo.techeermarket.global.exception.user.UserEmailAlreadyExistsException;
import com.teamjo.techeermarket.global.exception.user.UserNotFoundException;
import com.teamjo.techeermarket.global.jwt.JwtUtill;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

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
    private JwtUtill jwtUtill;

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
    //  유저 정보 조회
    */
    @Transactional(readOnly = true)
    public UserDetailResponseDto getUserInfo(String email) {

        Users userEntity = userRepository.findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        return UserFromMapper.fromEntity(userEntity);
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









}
