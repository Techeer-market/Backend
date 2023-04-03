package com.teamjo.techeermarket.domain.users.service;

import com.teamjo.techeermarket.domain.users.dto.request.UsersRequestDto;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.mapper.UsersMapper;
import com.teamjo.techeermarket.domain.users.repository.UserRepository;
//import com.teamjo.techeermarket.global.security.JwtToken;
//import com.teamjo.techeermarket.global.security.JwtTokenProvider;
import com.teamjo.techeermarket.global.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UsersApiService {

    public final UserRepository userRepository;

    public final UsersMapper usersMapper;

    public final PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final S3Service s3Service;

//    private final JwtTokenProvider jwtTokenProvider;


    @Transactional
    public Users signup(UsersRequestDto usersRequestDto) {
        try {
            String thumbnailImageUrl;

            // 파일 존재 여부 확인 후 thumbnailImageUrl 설정
            if(usersRequestDto.getThumbnailImage() != null && !usersRequestDto.getThumbnailImage().isEmpty()) {
                thumbnailImageUrl = s3Service.uploadImage(usersRequestDto.getThumbnailImage());
            } else {
                thumbnailImageUrl = "https://techeermarket-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/bfe7def1-2f96-4a0e-a4a8-0255ee6bd874/default_profile.png";
            }

            usersRequestDto.setThumbnailImageUrl(thumbnailImageUrl);
            String password = passwordEncoder.encode(usersRequestDto.getPassword());
            usersRequestDto.setPassword(password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userRepository.save(usersMapper.toEntity(usersRequestDto));
    }

//    public JwtToken login(String email, String password) {
//        // Authentication 객체 생성
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
//        log.info(authenticationToken.toString());
////        System.out.println(authenticationManagerBuilder.getObject());
//
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//
//        // 검증된 인증 정보로 JWT 토큰 생성
//        JwtToken token = jwtTokenProvider.generateToken(authentication);
//
//        return token;
//    }

}
