package com.teamjo.techeermarket.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamjo.techeermarket.domain.users.entity.Users;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

// 로그인을 시도할 때 JWT 토큰을 생성하고, 해당 토큰을 쿠키에 저장하여 응답에 추가하는 기능
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtill jwtUtill;

    public JwtLoginFilter(AuthenticationManager authenticationManager, JWTUtill jwtUtill) {
        this.authenticationManager = authenticationManager;
        this.jwtUtill = jwtUtill;
    }

    // 사용자의 로그인 정보를 받아 인증을 시도
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Users user = new ObjectMapper().readValue(request.getInputStream(), Users.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getName(),
                            user.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 인증에 성공한 경우 JWT 토큰을 생성하여 쿠키에 저장
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Users user = (Users) authResult.getPrincipal();
        String accessToken = jwtUtill.makeAccessToken(user);
        String refreshToken = jwtUtill.makeRefreshToken(user);

        Cookie accessTokenCookie = new Cookie("access_token", accessToken);
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

}
