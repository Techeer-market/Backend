package com.teamjo.techeermarket.domain.users.entity;

import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.global.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@ToString
@Setter
@Getter
public class Users extends BaseEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID userUuid;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "social")
    private String social;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "role", nullable = false)
//    private Role role;

    @Column(name = "name")
    private String name;

    @Column(name = "birthday")
    private LocalDateTime birthday;

    @Column(name = "thumbnail_url",length = 500)
    private String thumbnailUrl;

    @OneToMany (mappedBy = "users")
    private List<Products> products;

//    public String getRoleKey() {
//        return this.role.getKey();
//    }
//}


    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}