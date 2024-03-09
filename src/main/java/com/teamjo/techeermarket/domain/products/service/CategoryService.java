package com.teamjo.techeermarket.domain.products.service;

import com.teamjo.techeermarket.domain.products.dto.response.ProductPreViewDto;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoryService {
    @Transactional(readOnly = true)
    List<ProductPreViewDto> getCategoryList(Long categoryId, int pageNo, int pageSize);

    @Transactional(readOnly = true)
    Page<ProductPreViewDto> searchByTitleInCategory(Long categoryId, int pageNo, int pageSize, String search);
}
