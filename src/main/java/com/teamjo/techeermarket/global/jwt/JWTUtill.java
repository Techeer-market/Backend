package com.teamjo.techeermarket.global.jwt;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.teamjo.techeermarket.global.exception.user.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Date;

@Component
public class JWTUtill {
    private static final long AUTH_TIME = 3 * 60 * 60 * 1000L;       // 3 hours
    private static final long REFRESH_TIME = 15 * 24 * 60 * 60 * 1000L;   // 15 days

    @Value("${jwt.secret}")  // Get the secret key from application.properties
    private String secretKey;

    private Algorithm algorithm;

    @PostConstruct
    private void init() {
        this.algorithm = Algorithm.HMAC512(secretKey);
    }

    // 사용자 정보를 받아와서 인증 토큰을 생성
    public String makeAccessToken(Users user) {

        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + AUTH_TIME))
                .sign(algorithm);
    }

    // 사용자 정보를 받아와서 리프레시 토큰을 생성
    public String makeRefreshToken(Users user) {

        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TIME))
                .sign(algorithm);
    }

    // 토큰 검증
    public void validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            if (!jwt.getExpiresAt().after(Date.from(Instant.now()))) {
                throw new InvalidTokenException(); // 만료된 토큰
            }
        } catch (JWTVerificationException e) {
            // 토큰이 유효하지 않을 때의 처리
            throw new InvalidTokenException(); // 유효하지 않은 토큰
        }
    }


    // 토큰에서 이메일 정보를 추출
    public String getEmailFromToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

}