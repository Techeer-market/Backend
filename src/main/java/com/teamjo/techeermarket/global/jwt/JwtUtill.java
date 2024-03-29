package com.teamjo.techeermarket.global.jwt;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtill {
    private static final long AUTH_TIME = 3 * 60 * 60 * 1000L;       // 3 hours
    private static final long REFRESH_TIME = 30 * 24 * 60 * 60 * 1000L;   // 30 days

//    @Value("${jwt.secret}")
//    private static String secretKey;
//    public static final Algorithm ALGORITHM = Algorithm.HMAC256(secretKey);
    public static final Algorithm ALGORITHM = Algorithm.HMAC256("hahaha");


    // 사용자 정보를 받아와서 인증 토큰을 생성
    public String makeAccessToken(Users user) {

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("exp", Instant.now().getEpochSecond() + AUTH_TIME)
                .sign(ALGORITHM);
    }

    // 사용자 정보를 받아와서 리프레시 토큰을 생성
    public String makeRefreshToken(Users user) {

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("exp", Instant.now().getEpochSecond() + REFRESH_TIME)
                .sign(ALGORITHM);
    }

    // 주어진 JWT 토큰을 검증
    public static VerifyResultDto verify(String token) {
        try {
            DecodedJWT verify = JWT.require(ALGORITHM).build().verify(token);
            return VerifyResultDto.builder().success(true)
                    .userEmail(verify.getSubject()).build();

        } catch (Exception ex) {
            DecodedJWT decode = JWT.decode(token);
            return VerifyResultDto.builder()
                    .success(false)
                    .userEmail(decode.getSubject())
                    .build();
        }
    }



    // 토큰이 유효한지 검증
    public boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(ALGORITHM).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getExpiresAt().after(Date.from(Instant.now()));
        } catch (JWTVerificationException e) {
            return false; // 토큰이 유효하지 않음
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

    // 리프레시 토큰이 만료되었는지 확인
    public boolean isRefreshTokenExpired(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return !jwt.getExpiresAt().after(Date.from(Instant.now()));
        } catch (Exception e) {
            return true;
        }
    }


    // 토큰의 남은 시간 확인
    public long getRemainingDays(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            long diff = jwt.getExpiresAt().getTime() - System.currentTimeMillis();
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return 0;
        }
    }


}