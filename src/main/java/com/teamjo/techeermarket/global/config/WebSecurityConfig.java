package com.teamjo.techeermarket.global.config;

import com.teamjo.techeermarket.global.jwt.JwtAuthenticationEntryPoint;
import com.teamjo.techeermarket.global.jwt.JwtCheckFilter;
import com.teamjo.techeermarket.global.jwt.JwtLoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final CorsConfig config;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private static final String[] AUTH_WHITE_LIST = {
            "/api/users/signup",
            "/api/users/login",
            "/error",
            "/h2-console/**"
    };

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequestsConfigurer -> {
                    for (String path : AUTH_WHITE_LIST) {
                        authorizeHttpRequestsConfigurer.antMatchers(String.valueOf(new AntPathRequestMatcher(path))).permitAll();
                    }
                })
                .apply(new MyCustomDsl());
        return http.build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) {
            AuthenticationManager manager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilter(config.corsFilter())
                    .addFilterAt(new JwtLoginFilter(manager), UsernamePasswordAuthenticationFilter.class)
                    .addFilterAt(new JwtCheckFilter(manager, userDetailsServiceImpl), BasicAuthenticationFilter.class);
        }
    }

}


