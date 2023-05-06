package com.teamjo.techeermarket.domain.products.service;

import com.teamjo.techeermarket.domain.category.entity.Categorys;
import com.teamjo.techeermarket.domain.category.repository.CategoryRepository;
import com.teamjo.techeermarket.domain.products.dto.request.ProductRequestDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductInfo;
import com.teamjo.techeermarket.domain.products.dto.response.ProductPageDto;
import com.teamjo.techeermarket.domain.products.dto.response.ProductResponseDto;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.products.mapper.ProductMapper;
import com.teamjo.techeermarket.domain.products.repository.ProductRepository;
import com.teamjo.techeermarket.global.common.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductAPiService {

    public final ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private final ProductMapper productMapper;

    @Transactional
    public Products postProduct(ProductRequestDto productRequestDto){

        Categorys findcategory = categoryRepository.findByCategoryUuid(productRequestDto.getCategoryUuid());

        return productRepository.save(productMapper.toEntity(productRequestDto, findcategory));
    }

//    @Transactional(readOnly = true)
//    public ProductPageDto<ProductResponseDto> getAllProduct(int pageNo, int pageSize) {
//        PageRequest paging = PageRequest.of(pageNo, pageSize);
//        Page<Products> products = productRepository.findAllProductsWithPagination(paging);
//        List<ProductResponseDto> productDtos = products.getContent().stream()
//                .map(productMapper::fromEntity)
//                .collect(Collectors.toList());
//        PageInfo pageInfo = new PageInfo(pageNo, pageSize, (int) products.getTotalElements(), products.getTotalPages());
//        return new ProductPageDto<>(productDtos, pageInfo);
//    }

    @Transactional(readOnly = true)
    public List<ProductInfo> getAllProductList (int pageNo, int pageSize) {
        PageRequest paging = PageRequest.of(pageNo, pageSize);
        return productRepository.findAllProductsWithPagination(paging).stream()
              .map(productMapper::fromListEntity).collect(Collectors.toList());
    }
}
