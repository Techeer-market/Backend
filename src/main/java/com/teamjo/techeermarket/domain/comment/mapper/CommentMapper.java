package com.teamjo.techeermarket.domain.comment.mapper;

import com.teamjo.techeermarket.domain.comment.dto.request.CommentRequestDto;
import com.teamjo.techeermarket.domain.comment.dto.response.CommentResponseDto;
import com.teamjo.techeermarket.domain.comment.entity.Comments;
import com.teamjo.techeermarket.domain.products.entity.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    public Comments toEntity(CommentRequestDto commentRequestDto, Products products) {
        return Comments.builder()
                .commentUuid(UUID.randomUUID())
                .content(commentRequestDto.getContent())
                .products(products)
                .build();
    }

    public CommentResponseDto fromEntity(Comments comments) {
        return CommentResponseDto.builder()
                .commentUuid(comments.getCommentUuid())
                .content(comments.getContent())
                .productUuid(comments.getProducts().getProductUuid())
                .createdDate(comments.getCreatedDate())
                .modifiedDate(comments.getModifiedDate())
                .build();
    }


}
