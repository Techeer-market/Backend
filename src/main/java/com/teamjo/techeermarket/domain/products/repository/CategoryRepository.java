package com.teamjo.techeermarket.domain.products.repository;

import com.teamjo.techeermarket.domain.products.entity.Categorys;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Categorys, Long> {
    Categorys findIdByName(String Name);  //카테고리 이름으로 카테고리 id 찾기

    Categorys findCategorysById(Long categoryId);
}
