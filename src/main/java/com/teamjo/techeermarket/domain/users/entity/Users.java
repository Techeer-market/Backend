package com.teamjo.techeermarket.domain.users.entity;

import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.global.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@ToString
@Setter
@Getter
@Table(name="users")
public class Users extends BaseEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)  // 이름
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "birthday")
    private String birthday ;

    @Column(name = "profile_url")
    private String profileUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "social")
    private Social social;

    @OneToMany(mappedBy = "users")
    private List<Products> products;

}