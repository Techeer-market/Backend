package com.teamjo.techeermarket.domain.mypage.entity;

import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.users.entity.Users;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@AllArgsConstructor
@Entity
@Setter
@Builder
@NoArgsConstructor
@Table(name="user_product_like")
public class UserLike {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Products products;

}
