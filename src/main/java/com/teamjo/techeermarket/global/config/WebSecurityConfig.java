package com.teamjo.techeermarket.global.config;


import com.teamjo.techeermarket.domain.users.repository.UserRepository;
import com.teamjo.techeermarket.global.jwt.JWTUtill;
import com.teamjo.techeermarket.global.jwt.JwtAuthenticationEntryPoint;
import com.teamjo.techeermarket.global.jwt.JwtCheckFilter;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.CorsFilter;
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

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final CorsConfig config;
    private final UserRepository userRepository;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JWTUtill jwtUtill;
    private final CustomUserDetailsService customUserDetailsService;
    private static final String[] AUTH_WHITE_LIST = {
            "/api/users/signup",
            "/api/users/login",
            "/h2-console/**"
    };

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers(AUTH_WHITE_LIST).permitAll()
                                .anyRequest().authenticated()
                );

        // JwtCheckFilter를 "/api/**" 경로에만 적용
        http.antMatcher("/api/post/**")
                .addFilterBefore(new JwtCheckFilter(jwtUtill), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}