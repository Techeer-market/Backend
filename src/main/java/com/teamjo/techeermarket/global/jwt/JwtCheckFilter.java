package com.teamjo.techeermarket.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.global.config.UserDetailsImpl;
import com.teamjo.techeermarket.global.config.UserDetailsServiceImpl;
import com.teamjo.techeermarket.global.exception.ErrorResponse;
import com.teamjo.techeermarket.global.exception.user.InvalidTokenException;
import com.teamjo.techeermarket.global.exception.user.NoTokenException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class JwtCheckFilter extends BasicAuthenticationFilter {
    private UserDetailsServiceImpl userDetailsServiceImpl;

    List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

    private ObjectMapper objectMapper = new ObjectMapper();

    public JwtCheckFilter(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsServiceImpl) {
        super(authenticationManager);
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    // JWT 토큰 확인 후 인증 정보 설정
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String bearer = request.getHeader("Access-Token");

        if(bearer == null || bearer.startsWith("access_token ")){
            chain.doFilter(request,response);
            return;
        }
        String token = bearer.substring("access_token:".length());  // 헤더에서 토큰 추출
        VerifyResultDto result = JwtUtill.verify(token);              // 토큰 검증


        // 토큰이 유효하다면
        if (result.isSuccess()) {
            UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetailsServiceImpl.loadUserByUsername(result.getUserEmail());
            Users user = userDetailsImpl.getUser();

            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                    userDetailsImpl, authorities
            );

//            System.out.println("userToken = " + userToken.getName());   // 토큰 체크 잘 들어오는지 확인 O
//            System.out.println("userToken = " + userToken);   // 토큰 체크 잘 들어오는지 확인 O

            SecurityContextHolder.getContext().setAuthentication(userToken);

            chain.doFilter(request,response);

            // 토큰이 유효하지 않다면
        } else {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

           ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다.");
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
           response.getOutputStream().write(objectMapper.writeValueAsBytes(errorResponse));
        }







//        // 헤더에서 액세스 토큰 추출
//        String authorizationHeader = request.getHeader("Access-Token");
//        if (authorizationHeader != null && authorizationHeader.startsWith("access_token:")) {
//            String accessToken = authorizationHeader.substring(7); // "access_token: " 이후의 토큰 부분 추출
//
//            // 액세스 토큰이 있는 경우, 토큰을 검증
//            if (accessToken != null) {
//                jwtUtill.validateToken(accessToken);
//
//                String email = jwtUtill.getEmailFromToken(accessToken);
//                UsernamePasswordAuthenticationToken authentication =
//                        new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } else {
//                throw new NoTokenException();
//            }
//
//            filterChain.doFilter(request, response);
//        }


    }
}