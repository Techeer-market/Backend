//package com.teamjo.techeermarket.global.jwt;
//
//import com.teamjo.techeermarket.domain.users.entity.Users;
//import com.teamjo.techeermarket.domain.users.repository.UserRepository;
//import com.teamjo.techeermarket.global.exception.user.InvalidTokenException;
//import com.teamjo.techeermarket.global.exception.user.UserNotFoundException;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//
//public class JwtRefreshFilter extends OncePerRequestFilter {
//    private final JWTUtill jwtUtill;
//    private final UserRepository userRepository;
//
//    public JwtRefreshFilter(JWTUtill jwtUtill, UserRepository userRepository) {
//        this.jwtUtill = jwtUtill;
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        String refreshToken = null;
//
//        // 쿠키에서 리프레시 토큰 추출
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("refresh_token".equals(cookie.getName())) {
//                    refreshToken = cookie.getValue();
//                }
//            }
//        }
//
//        try {
//            // 리프레시 토큰이 있는 경우, 토큰을 검증하고 새 액세스 토큰을 발급
//            if (refreshToken != null && !jwtUtill.isRefreshTokenExpired(refreshToken)) {
//                jwtUtill.validateToken(refreshToken);
//
//                String username = jwtUtill.getEmailFromToken(refreshToken);
//                Users user = userRepository.findByEmail(username)
//                        .orElseThrow(() -> new UserNotFoundException());
//
//                String newAccessToken = jwtUtill.makeAccessToken(user);
//
//                // 새로운 액세스 토큰을 HTTP 응답의 쿠키에 추가
//                Cookie newAccessTokenCookie = new Cookie("access_token", newAccessToken);
//                response.addCookie(newAccessTokenCookie);
//
//                // 리프레시 토큰이 10일 미만으로 남았다면 리프레시 토큰도 갱신
//                if (jwtUtill.getRemainingDays(refreshToken) < 10) {
//                    String newRefreshToken = jwtUtill.makeRefreshToken(user);
//                    Cookie newRefreshTokenCookie = new Cookie("refresh_token", newRefreshToken);
//                    response.addCookie(newRefreshTokenCookie);
//                }
//            }
//        } catch (InvalidTokenException e) {
//            // 리프레시 토큰이 유효하지 않을 때, 필요한 처리를 추가할 수 있습니다.
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
