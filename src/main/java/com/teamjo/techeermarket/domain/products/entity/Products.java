package com.teamjo.techeermarket.domain.products.entity;

import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.global.common.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Products extends BaseEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private Users users;

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
    private ProductState productState;

    @Column(name = "views", nullable = false)
    private int views;

    @Builder
    public Products(String title, String description, int price ){
        this.title = title;
        this.description = description;
        this.price = price;
    }


}
