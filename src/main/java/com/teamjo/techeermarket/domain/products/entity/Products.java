package com.teamjo.techeermarket.domain.products.entity;

import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.global.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Products extends BaseTimeEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    // category_id

    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "product_uuid")  // uuid 나중에 추가
    private UUID productUuid ;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductState state;

    @Column(name = "views", nullable = false)
    private int views;

    @Builder
    public Products(String title, String description, Users users, int price ){
        this.title = title;
        this.description = description;
        this.users = users;
        this.price = price;
    }


}
