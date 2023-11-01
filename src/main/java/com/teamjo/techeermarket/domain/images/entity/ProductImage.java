package com.teamjo.techeermarket.domain.images.entity;

import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.global.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@AllArgsConstructor
@Entity
@Setter
@Builder
@NoArgsConstructor
@Table(name="product_image")
public class ProductImage extends BaseEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Products products;

    @Column(name = "image_name", nullable = false)
    private String imageName;

    @Column(name = "image_num", nullable = false)
    private int imageNum;

    @Column(name = "image_url")
    private String imageUrl;


}