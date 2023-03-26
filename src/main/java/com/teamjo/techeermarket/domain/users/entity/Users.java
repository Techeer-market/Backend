package com.teamjo.techeermarket.domain.users.entity;

import com.teamjo.techeermarket.global.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@ToString
public class Users extends BaseEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


//    @GenericGenerator(name = "uuid2", strategy = "uuid2")
//    @Column(name = "user_uuid", columnDefinition = "BINARY(16)")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "user_uuid", length = 36, nullable = false, updatable = false)
    private UUID userUuid;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "social")
    private String social;

//    @Enumerated(EnumType.STRING)
////    @Column(nullable = false)
//    private Role role;


    @Column(name = "name")
    private String name;

    @Column(name = "birthday")
    private LocalDateTime birthday;

    @Column(name = "thumb_nail_url")
    private String thumbnailUrl;

//    public String getRoleKey(){
//        return this.role.getKey();
//    }

}
