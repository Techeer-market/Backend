package com.teamjo.techeermarket.domain.products.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.global.common.BaseEntity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.List;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Getter
@AllArgsConstructor
@Entity
@Setter
@Builder
@NoArgsConstructor
@Table(name="products")
public class Products extends BaseEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Categorys categorys;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "views", nullable = false)
    private int views;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductState productState ;



}
