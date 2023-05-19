package com.teamjo.techeermarket.domain.users.service;

import com.teamjo.techeermarket.domain.users.dto.request.AuthTokenRequest;
import com.teamjo.techeermarket.domain.users.dto.request.UsersSignupRequestDto;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.mapper.UsersMapper;
import com.teamjo.techeermarket.domain.users.repository.UserNotFoundException;
import com.teamjo.techeermarket.domain.users.repository.UserRepository;
import com.teamjo.techeermarket.global.jwt.JwtTokenProvider;
import com.teamjo.techeermarket.global.jwt.TokenInfo;
import com.teamjo.techeermarket.global.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;



import java.util.Optional;
import java.util.UUID;

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

    private final Environment env;

    private final JwtTokenProvider jwtTokenProvider;



    @Transactional
    public Users signup(UsersSignupRequestDto usersSignupRequestDto) {
        try {
            // 이메일 중복 확인
            if(userRepository.existsByEmailAndSocial(usersSignupRequestDto.getEmail(), "local")){
                return null;
            } else {
                String thumbnailImageUrl;

                // 파일 존재 여부 확인 후 thumbnailImageUrl 설정
                if(usersSignupRequestDto.getThumbnailImage() != null && !usersSignupRequestDto.getThumbnailImage().isEmpty()) {
                    thumbnailImageUrl = s3Service.uploadImage(usersSignupRequestDto.getThumbnailImage());
                } else {
                    thumbnailImageUrl = "https://techeermarket-bucket.s3.ap-northeast-2.amazonaws.com/thumbnails/bfe7def1-2f96-4a0e-a4a8-0255ee6bd874/default_profile.png";
                }

                usersSignupRequestDto.setThumbnailImageUrl(thumbnailImageUrl);
                String password = passwordEncoder.encode(usersSignupRequestDto.getPassword());
                usersSignupRequestDto.setPassword(password);

                return userRepository.save(usersMapper.toEntity(usersSignupRequestDto));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResponseEntity getKaKaoAccessToken(String code){
        RestTemplate restTemplate = new RestTemplateBuilder().build();

        AuthTokenRequest authTokenRequest = new AuthTokenRequest();
        authTokenRequest.setGrantType("authorization_code");
//        authTokenRequest.setClientId(env.getProperty("security.oauth2.client.registration.kakao.client-id"));
        authTokenRequest.setClientId("b0a4f785ed460ba74d3b23c3fd538e2a");
        authTokenRequest.setRedirectUri("http://localhost:3000/auth/kakao");
        authTokenRequest.setCode(code);
//        authTokenRequest.setClientSecret(env.getProperty("security.oauth2.client.registration.kakao.client_secret"));
        if (StringUtils.isEmpty(authTokenRequest.getClientId()) || StringUtils.isEmpty(authTokenRequest.getRedirectUri())
                || StringUtils.isEmpty(authTokenRequest.getCode())) {
            return ResponseEntity.badRequest().body("필수 파라미터 불충족");
        }

        String clientSecret = "";
        if (StringUtils.isEmpty(authTokenRequest.getClientSecret())) clientSecret = authTokenRequest.getClientSecret();

        UriComponents builder = UriComponentsBuilder.newInstance()
                .scheme("https").host("kauth.kakao.com")
                .path("/oauth/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", authTokenRequest.getCode())
                .queryParam("redirect_uri", authTokenRequest.getRedirectUri())
                .queryParam("client_id", authTokenRequest.getClientId())
//                .queryParam("client_secret", clientSecret)
                .build();

        String response = restTemplate.postForObject(builder.toUriString(), authTokenRequest, String.class);
        return ResponseEntity.ok().body(response);

    }
    @Transactional
    public ResponseEntity getUserInfoFromKaKao(String access_Token){
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(access_Token);

        UriComponents builder = UriComponentsBuilder.newInstance()
                .scheme("https").host("kapi.kakao.com")
                .path("v2/user/me")
                .build();


        HttpEntity request = new HttpEntity(headers);

//        Users user = new Users();


        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                request,
                String.class
        );
    }




    @Transactional
    public TokenInfo login(String memberId, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }


    @Transactional
    public void deleteUser(UUID userUuid) {
        Users users = userRepository.findByUserUuid(userUuid);
        if (users == null) {
            throw new UserNotFoundException("User not found: " + userUuid);
        }
        users.setIsDeleted(true);
        userRepository.save(users);
    }


//    @Transactional
//    public UsersResponseDto updateUser(UUID userUuid, UsersSignupRequestDto usersUpdateDto) {
//        Optional<Users> optionalUser = userRepository.findByUserUuid(userUuid);
//
//        if (optionalUser.isPresent()) {
//            Users user = optionalUser.get();
//
//            // 회원정보 수정 로직 적용
//            Users updatedUser = usersMapper.updateEntity(usersUpdateDto, user);
//
//            return userRepository.save(updatedUser);
//        } else {
//            throw new NotFoundException("User not found with userUuid: " + userUuid);
//        }
//    }


}
