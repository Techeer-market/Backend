package com.teamjo.techeermarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableCaching
@EnableJpaAuditing
@SpringBootApplication
public class TecheermarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(TecheermarketApplication.class, args);
    }

}