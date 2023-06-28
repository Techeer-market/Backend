package com.teamjo.techeermarket.domain.users.service;

import com.google.gson.Gson;
import com.teamjo.techeermarket.domain.users.dto.request.AuthTokenRequest;
import com.teamjo.techeermarket.domain.users.dto.request.UsersLoginRequestDto;
import com.teamjo.techeermarket.domain.users.dto.request.UsersSignupRequestDto;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.mapper.UsersMapper;
import com.teamjo.techeermarket.domain.users.repository.UserNotFoundException;
import com.teamjo.techeermarket.domain.users.repository.UserRepository;
//import com.teamjo.techeermarket.global.jwt.JwtTokenProvider;
//import com.teamjo.techeermarket.global.jwt.TokenInfo;
//import com.teamjo.techeermarket.global.s3.S3ProfileService;
import com.teamjo.techeermarket.global.s3.S3ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.core.Authentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManager;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UsersApiService {

    public final UserRepository userRepository;

    public final UsersMapper usersMapper;


//    public final PasswordEncoder passwordEncoder;

//    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final S3ProfileService s3ProfileService;

    private final Environment env;

//    private final AuthenticationManager authenticationManager;

//    private final JwtTokenProvider jwtTokenProvider;

    boolean test = false;

    @Transactional
    public Users signup(UsersSignupRequestDto usersSignupRequestDto) {
        try {
            // 이메일 중복 확인
            log.info("signUp :: userSignUpRequestDto :: {}", usersSignupRequestDto);
            if (userRepository.existsByEmailAndSocial(usersSignupRequestDto.getEmail(), "local")) {
                return null;
            } else {
                String thumbnailImageUrl;
//
//                // 파일 존재 여부 확인 후 thumbnailImageUrl 설정
//                if (usersSignupRequestDto.getThumbnailImage() != null && !usersSignupRequestDto.getThumbnailImage().isEmpty()) {
//                    thumbnailImageUrl = s3ProfileService.uploadImage(usersSignupRequestDto.getThumbnailImage());
//                } else {
//                    thumbnailImageUrl = "https://techeer-market-bucket.s3.ap-northeast-2.amazonaws.com/95288297.png";
//                }

//                usersSignupRequestDto.setThumbnailImageUrl(thumbnailImageUrl);
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String password = passwordEncoder.encode(usersSignupRequestDto.getPassword());
                usersSignupRequestDto.setPassword(password);
//                String password = passwordEncoder.encode(usersSignupRequestDto.getPassword());
//                usersSignupRequestDto.setPassword(password);


                return userRepository.save(usersMapper.toEntity(usersSignupRequestDto));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Users login(UsersLoginRequestDto usersLoginRequestDto){
        String email = usersLoginRequestDto.getEmail();
        String password = usersLoginRequestDto.getPassword();

        Users users = userRepository.findByEmail(email);
        log.info("UsersApiService :: login :: users :: {}",users);

        if(users != null && passwordMatches(password,users.getPassword())){
            return users;
        }
        return null;
    }

    private boolean passwordMatches(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public ResponseEntity getKaKaoAccessToken(String code) {
//        if(test==true){
//            return null;
//        }else {
//            test= true;
        Map<String,String> response = null;
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        log.info("getKaKaoAccessToken");
        AuthTokenRequest authTokenRequest = new AuthTokenRequest();
        authTokenRequest.setGrantType("authorization_code");
        authTokenRequest.setClientId(env.getProperty("security.oauth2.client.registration.kakao.client-id"));
//            authTokenRequest.setClientId("b0a4f785ed460ba74d3b23c3fd538e2a");
        authTokenRequest.setRedirectUri(env.getProperty("security.oauth2.client.registration.kakao.redirect-uri"));
        authTokenRequest.setCode(code);
        authTokenRequest.setClientSecret(env.getProperty("security.oauth2.client.registration.kakao.client-secret"));
        if (StringUtils.isEmpty(authTokenRequest.getClientId()) || StringUtils.isEmpty(authTokenRequest.getRedirectUri())
                || StringUtils.isEmpty(authTokenRequest.getCode())) {
            return ResponseEntity.badRequest().body("필수 파라미터 불충족");
        }

        String clientSecret = "";
        if (StringUtils.isEmpty(authTokenRequest.getClientSecret()))
            clientSecret = authTokenRequest.getClientSecret();

        UriComponents builder = UriComponentsBuilder.newInstance()
                .scheme("https").host("kauth.kakao.com")
                .path("/oauth/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", authTokenRequest.getCode())
                .queryParam("redirect_uri", authTokenRequest.getRedirectUri())
                .queryParam("client_id", authTokenRequest.getClientId())
                .queryParam("client_secret", authTokenRequest.getClientSecret())
                .build();

        response = restTemplate.postForObject(builder.toUriString(), authTokenRequest, Map.class);
        String accessToken = response.get("access_token");
        String refreshToken = response.get("refresh_token");
        log.info("getKaKaoAccessToken :: accessToken :: {}", accessToken);
        log.info("getKaKaoAccessToken :: refreshToken :: {}", refreshToken);

//        getUserInfoFromKaKao(accessToken);

        return ResponseEntity.ok().body(response);
//        }

    }



//    @Transactional
//    public String getUserInfoFromKaKao(String access_Token) {
//        RestTemplate restTemplate = new RestTemplateBuilder().build();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(access_Token);
//        ArrayList<String> properties = new ArrayList<>();
//        properties.add("kakao_account.name");
//        properties.add("kakao_account.birthday");
//        UriComponents builder = UriComponentsBuilder.newInstance()
//                .scheme("https").host("kapi.kakao.com")
//                .path("v2/user/me")
////                .queryParam("property_keys",new Gson().toJson(properties))
//                .build();
//
//
//        HttpEntity request = new HttpEntity(headers);
//
////        Users user = new Users();
//
//        ResponseEntity<String>response = restTemplate.exchange(
//                builder.toUriString(),
//                HttpMethod.GET,
//                request,
//                String.class);
//        log.info("getUserInfoFromKakao :: response body :: {}", response.getBody());
//        try {
//            JSONObject jsonObject = new JSONObject(response.getBody());
//
//            Long id = jsonObject.getLong("id");
//            String connectedAt = jsonObject.getString("connected_at");
//
//            JSONObject propertiesRes = jsonObject.getJSONObject("properties");
//            String profileImage = propertiesRes.getString("profile_image");
//            String thumbnailImage = propertiesRes.getString("thumbnail_image");
//
//            JSONObject kakaoAccount = jsonObject.getJSONObject("kakao_account");
//            boolean profileImageNeedsAgreement = kakaoAccount.getBoolean("profile_image_needs_agreement");
//
//            JSONObject profile = kakaoAccount.getJSONObject("profile");
//            String thumbnailImageUrl = profile.getString("thumbnail_image_url");
//            String profileImageUrl = profile.getString("profile_image_url");
//            boolean isDefaultImage = profile.getBoolean("is_default_image");
//
//            boolean hasEmail = kakaoAccount.getBoolean("has_email");
//            boolean emailNeedsAgreement = kakaoAccount.getBoolean("email_needs_agreement");
//
//            // Use the parsed values as needed
//            System.out.println("id: " + id);
//            System.out.println("connected_at: " + connectedAt);
//            System.out.println("profile_image: " + profileImage);
//            System.out.println("thumbnail_image: " + thumbnailImage);
//            System.out.println("profile_image_needs_agreement: " + profileImageNeedsAgreement);
//            System.out.println("thumbnail_image_url: " + thumbnailImageUrl);
//            System.out.println("profile_image_url: " + profileImageUrl);
//            System.out.println("is_default_image: " + isDefaultImage);
//            System.out.println("has_email: " + hasEmail);
//            System.out.println("email_needs_agreement: " + emailNeedsAgreement);
//
//            if(userRepository.findById(id)==null){
//                UsersSignupRequestDto usersSignupRequestDto = new UsersSignupRequestDto();
//                usersSignupRequestDto.setThumbnailImageUrl(thumbnailImageUrl);
//                usersSignupRequestDto.setId(id);
//            }
//
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
//    }catch(Exception e){
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//

//    @Transactional
//    public TokenInfo login(String username, String password) {
//        // 1. 로그인 ID와 비밀번호를 기반으로 Authentication 객체 생성
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//
//        // 2. 실제 인증(authentication)이 이루어지는 부분
//        Authentication authentication = authenticationManager.authenticate(authenticationToken);
//
//        // 3. 인증 정보를 기반으로 JWT 토큰 생성
//        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
//
//        return tokenInfo;
//    }


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
