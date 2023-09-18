package com.teamjo.techeermarket.domain.mypage.entity;

import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@AllArgsConstructor
@Entity
@Builder
@NoArgsConstructor
@Table(name="user_purchase")
public class UserPurchase extends BaseEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany (mappedBy = "users")
    private List<Products> products;

    @Column(name = "seller_id", nullable = false)   // FK user 연결
    private String sellerId;

    @Column(name = "buyer_id", nullable = false)    // FK user 연결
    private String buyerId;


}
