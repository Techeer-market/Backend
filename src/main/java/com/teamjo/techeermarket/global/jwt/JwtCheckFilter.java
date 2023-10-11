package com.teamjo.techeermarket.global.jwt;

import com.teamjo.techeermarket.domain.users.repository.UserRepository;
import com.teamjo.techeermarket.global.exception.user.InvalidTokenException;
import com.teamjo.techeermarket.global.jwt.JWTUtill;
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


public class JwtCheckFilter extends OncePerRequestFilter {
    private final JWTUtill jwtUtill;
    private final UserRepository userRepository;

    public JwtCheckFilter(JWTUtill jwtUtill, UserRepository userRepository) {
        this.jwtUtill = jwtUtill;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, InvalidTokenException {
        String accessToken = null;

        // 쿠키에서 액세스 토큰 추출
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("access_token".equals(cookie.getName())) {
                    accessToken = cookie.getValue();
                }
            }
        }

        // 액세스 토큰이 있는 경우, 토큰을 검증
        if (accessToken != null) {
            jwtUtill.validateToken(accessToken);

            String username = jwtUtill.getEmailFromToken(accessToken);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }



}
