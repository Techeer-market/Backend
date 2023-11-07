package com.teamjo.techeermarket.global.config;

import com.teamjo.techeermarket.domain.users.entity.Users;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@Getter
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final Users user;

//    public UserDetailsImpl(Users user) {
//        this.user = user;
//    }

//    @Override
//    public Users getUser() {
//        return user;
//    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
//        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }



}