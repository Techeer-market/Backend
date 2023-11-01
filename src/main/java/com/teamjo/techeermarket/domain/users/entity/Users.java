package com.teamjo.techeermarket.domain.users.entity;

import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.global.common.BaseEntity;
import lombok.*;
import javax.persistence.*;
import java.util.List;


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

    @OneToMany(mappedBy = "users",fetch = FetchType.EAGER)
    private List<Products> products;

}