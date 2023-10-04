package com.teamjo.techeermarket.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);   // 쿠키 및 자격 증명 허용
        config.addAllowedOriginPattern("*"); // 모든 IP에 응답 허용

        config.addAllowedHeader("*");  // 모든 헤더 응답에 허용
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
