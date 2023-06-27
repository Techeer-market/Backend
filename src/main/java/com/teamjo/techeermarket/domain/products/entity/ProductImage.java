//package com.teamjo.techeermarket.domain.products.entity;
//
//
//import com.teamjo.techeermarket.global.common.BaseEntity;
//import lombok.*;
//import org.hibernate.annotations.GenericGenerator;
//
//import javax.persistence.*;
//import java.util.UUID;
//
//import static javax.persistence.FetchType.LAZY;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@Entity
//@Builder
//@NoArgsConstructor
//public class ProductImage extends BaseEntity {
//
//    @Id
//    @Column(name = "id", unique = true, nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "product_id", nullable = false)
//    private Products products;
//
////    @GenericGenerator(name = "uuid2", strategy = "uuid2")
////    @Column(name = "image_uuid" ,length = 36, nullable = false, updatable = false)
////    private UUID imageUuid ;
//
//    @Column(name = "image_url")
//    private String imageUrl;
//
//}
