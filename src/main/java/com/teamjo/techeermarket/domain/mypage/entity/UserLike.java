package com.teamjo.techeermarket.domain.mypage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Entity
@Builder
@NoArgsConstructor
@Table(name="user_product_like")
public class UserLike {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)   // FK user 연결
    private int userId;

    @Column(name = "product_id", nullable = false)    // FK product 연결
    private int productId;

}
