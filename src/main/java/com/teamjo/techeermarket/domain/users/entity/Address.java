package com.teamjo.techeermarket.domain.users.entity;

import com.teamjo.techeermarket.global.common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@ToString
@Setter
@Getter
@Table(name="address")
public class Address extends BaseEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "lat", nullable = false)  // 위도
    private String lat;

    @Column(name = "lon", nullable = false)  // 경도
    private String lon;

}