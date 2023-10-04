package com.teamjo.techeermarket.global.jwt;

import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.mapper.UserMapper;
import com.teamjo.techeermarket.domain.users.repository.UserRepository;
import com.teamjo.techeermarket.global.exception.user.InvalidTokenException;
import com.teamjo.techeermarket.global.exception.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class JwtCheckFilter extends OncePerRequestFilter {

    private final JWTUtill jwtUtill;

    @Autowired
    private UserRepository userRepository;

    public JwtCheckFilter(JWTUtill jwtUtill) {
        this.jwtUtill = jwtUtill;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String accessToken = null;
        String refreshToken = null;

        // 쿠키에서 토큰 추출
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("access_token".equals(cookie.getName())) {
                    accessToken = cookie.getValue();
                } else if ("refresh_token".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                }
            }
        }

        try {
            // 액세스 토큰이 있는 경우, 토큰을 검증하고 사용자를 인증
            if (accessToken != null) {
                jwtUtill.validateToken(accessToken); // Validate access token

                String username = jwtUtill.getEmailFromToken(accessToken);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);


                // 액세스 토큰이 없고 리프레시 토큰이 있는 경우, 리프레시 토큰을 검증하고 새 액세스 토큰을 발급
            } else if (refreshToken != null) {
                jwtUtill.validateToken(refreshToken);

                // 리프레시 토큰이 유효하다면 새로운 액세스 토큰을 생성
                String username = jwtUtill.getEmailFromToken(refreshToken);
                Users user = userRepository.findByEmail(username)
                        .orElseThrow(() -> new UserNotFoundException());


                String newAccessToken = jwtUtill.makeAccessToken(user);

                // 새로운 액세스 토큰을 HTTP 응답의 쿠키에 추가
                Cookie newAccessTokenCookie = new Cookie("access_token", newAccessToken);
                response.addCookie(newAccessTokenCookie);
            } else {
                throw new InvalidTokenException();
            }


        } catch (InvalidTokenException e) {    // 엑세스 토큰이 유효하지 않을 때, 오류 처리
            if (refreshToken != null) {
                // 리프레시 토큰이 유효하다면 새로운 액세스 토큰을 발급
                jwtUtill.validateToken(refreshToken);

                // 리프레시 토큰이 유효하다면 새로운 액세스 토큰을 생성
                String username = jwtUtill.getEmailFromToken(refreshToken);
                Users user = userRepository.findByEmail(username)
                        .orElseThrow(() -> new UserNotFoundException());


                String newAccessToken = jwtUtill.makeAccessToken(user);

                // 새로운 액세스 토큰을 HTTP 응답의 쿠키에 추가
                Cookie newAccessTokenCookie = new Cookie("access_token", newAccessToken);
                response.addCookie(newAccessTokenCookie);

            } else {
                // 리프레시 토큰이 없다면 오류 처리
                throw new InvalidTokenException();
            }
        }

        filterChain.doFilter(request, response);  //JWT 토큰의 유효성을 확인한 후, 필터 체인을 계속 진행하여 요청을 처리
    }

}
