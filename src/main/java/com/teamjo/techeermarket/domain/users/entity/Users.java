package com.teamjo.techeermarket.domain.users.entity;

import com.teamjo.techeermarket.global.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Users extends BaseTimeEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "user_uuid", columnDefinition = "BINARY(16)")
    private UUID userUuid;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @Column(name = "thumb_nail_url")
    private String thumbnailUrl;

    @Builder
    public Users(String email, String password, String name, Date birthday, String thumbnailUrl) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.thumbnailUrl = thumbnailUrl;
    }
}
