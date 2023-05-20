package com.teamjo.techeermarket.domain.products.entity;

import com.teamjo.techeermarket.domain.category.entity.Categorys;
import com.teamjo.techeermarket.domain.comment.entity.Comments;
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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private Users users;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "categorys")
    private Categorys categorys;

    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "product_uuid" ,length = 36, nullable = false, updatable = false)  // uuid 나중에 추가
    private UUID productUuid ;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductState productState ;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TradeType tradeType ;

    @Column(name = "views", nullable = false)
    private int views;

    @OneToMany(mappedBy = "products")
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "products")
    private List<Comments> comments;


    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }


}
