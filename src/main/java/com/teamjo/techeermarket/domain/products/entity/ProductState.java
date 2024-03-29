package com.teamjo.techeermarket.domain.products.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductState {
    SALE,      // 판매중
    RESERVED,  // 예약중
    SOLD,      // 판매완료

};
