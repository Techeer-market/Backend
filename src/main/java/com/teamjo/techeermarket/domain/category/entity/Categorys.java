package com.teamjo.techeermarket.domain.category.entity;

import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.global.common.BaseEntity;
import lombok.*;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Entity
@Builder
@NoArgsConstructor
@Table(name="categorys")
public class Categorys extends BaseEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "category_uuid", length = 36, nullable = false, updatable = false)
    private UUID categoryUuid;

    @OneToMany (mappedBy = "categorys")
    private List<Products> products;

    @Column(name = "name", nullable = false)
    private String name;

    public void update(String name) {
        this.name = name;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }






}
