package com.teamjo.techeermarket.global.security;

import com.teamjo.techeermarket.domain.users.entity.Users;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(Users user){
        this.name = user.getName();
        this.email = user.getEmail();
//        this.picture = user.getPicture();
    }
}