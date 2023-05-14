package com.teamjo.techeermarket.domain.users.service;

import com.teamjo.techeermarket.domain.users.dto.request.UsersSignupRequestDto;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.mapper.UsersMapper;
import com.teamjo.techeermarket.domain.users.repository.UserRepository;
import com.teamjo.techeermarket.global.jwt.JwtTokenProvider;
import com.teamjo.techeermarket.global.jwt.TokenInfo;
import com.teamjo.techeermarket.global.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;



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

    public String getKaKaoAccessToken(String code){
        String access_Token="";
        String refresh_Token ="";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=fadeb1cea8077be1a20d8cc98139a990"); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=http://localhost:3000/auth/kakao"); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            com.google.gson.JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            log.info("UsersApiService :: access_token :: {}",access_Token);
            log.info("UsersApiService :: refresh_token :: {}",refresh_Token);

            log.info("UserApiService :: userinfoFromKaKao :: {}", getUserInfoFromKaKao(access_Token));
            br.close();
            bw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
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

}
