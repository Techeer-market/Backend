package com.teamjo.techeermarket.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamjo.techeermarket.domain.users.dto.LoginRequestDto;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.global.config.UserDetailsImpl;
import com.teamjo.techeermarket.global.exception.ErrorResponse;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 로그인을 시도할 때 JWT 토큰을 생성하고, 해당 토큰을 쿠키에 저장하여 응답에 추가하는 기능
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JwtUtill jwt = new JwtUtill();

    List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));


    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setFilterProcessesUrl("/api/users/login");
    }


    // 사용자의 로그인 정보를 받아 인증을 시도
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest reqeust,
            HttpServletResponse response) throws AuthenticationException {

        //  요청 헤더에서 RefreshToken 을 읽어옴
        String refreshToken = reqeust.getHeader("Refresh-Token");

        // 로그인 정보
        LoginRequestDto userLogin = objectMapper.readValue(reqeust.getInputStream(), LoginRequestDto.class);

        // 리프레시 토큰이 없는 경우
        if (refreshToken == null) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userLogin.getEmail(), userLogin.getPassword(), authorities);
            return getAuthenticationManager().authenticate(token);

        } else {

            VerifyResultDto verify = JwtUtill.verify(refreshToken);

            if (verify.isSuccess()) {  // 리프레시 토큰 유효 -> 시간 확인 후 재발급 추가하기
                return new UsernamePasswordAuthenticationToken(
                        userLogin.getEmail(), userLogin.getPassword(), authorities);
            } else {
                throw new IllegalArgumentException("Refresh token expired");
            }
        }
    }

    // 로그인 성공 시 -> 응답 헤더에 추가
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        Users user = userDetails.getUser();

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("userId", user.getId());

        response.setHeader("Refresh-Token", "refresh_token:" + jwt.makeRefreshToken(user));
        response.setHeader("Access-Token", "access_token:" + jwt.makeAccessToken(user));

        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        // JSON 으로 유저 id 출력
        response.getWriter().write(jsonResponse.toString());
        response.setStatus(HttpServletResponse.SC_OK);

    }


    // 로그인 실패시
    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException, ServletException {

        SecurityContextHolder.clearContext();

//        super.unsuccessfulAuthentication(request, response, failed);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, "사용자를 찾을 수 없습니다");
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().write(objectMapper.writeValueAsBytes(errorResponse));
        response.setStatus(401);


    }



}

