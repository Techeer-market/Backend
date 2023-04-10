package com.teamjo.techeermarket.domain.category.repository;

import com.teamjo.techeermarket.domain.category.entity.Categorys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Categorys, Long> {

    Categorys findByCategoryUuid (UUID categoryUuid);
}
