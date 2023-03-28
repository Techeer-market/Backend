package com.teamjo.techeermarket.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors().and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                .antMatchers("/api/v1/test/permit-all").permitAll()
//                .antMatchers("/api/v1/test/auth").authenticated()
//                .antMatchers("/**").authenticated()
                .anyRequest().permitAll()
//                .and()
//                .formLogin().disable();
                .and()
                    .oauth2Login()
                    .userInfoEndpoint() // oauth2 로그인 성공 후 가져올 때의 설정들
                    // 소셜로그인 성공 시 후속 조치를 진행할 UserService 인터페이스 구현체 등록
                    .userService(customOAuth2UserService); // 리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시
//        httpSecurity.addFilterAfter(
//                jwtAuthenticationFilter,
//                CorsFilter.class
//        );
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static List<String> clients = Arrays.asList("google", "kakao", "naver");

    @Bean
    public InMemoryClientRegistrationRepository clientRegistrationRepository() {

        List<ClientRegistration> registrations = clients.stream()
                .map(c -> getRegistration(c))
                .filter(registration -> registration != null)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration getRegistration(String client) {
        if (client.equals("google")) {
            return CustomOAuth2Provider.GOOGLE.getBuilder(client).build();
        }
        if (client.equals("kakao")) {
            return CustomOAuth2Provider.KAKAO.getBuilder(client).build();
        }
        if (client.equals("naver")) {
            return CustomOAuth2Provider.NAVER.getBuilder(client).build();
        }
        return null;
    }
}

