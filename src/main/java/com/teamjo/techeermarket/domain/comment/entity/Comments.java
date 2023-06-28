package com.teamjo.techeermarket.domain.comment.entity;

import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Entity
@Builder
@NoArgsConstructor
@Table(name="comments")
public class Comments extends BaseEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "users")
//    private Users users;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "products")
    private Products products;

    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "comment_uuid", length = 36, nullable = false, updatable = false)
    private UUID commentUuid;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users")
    private Users users;

}
