package com.teamjo.techeermarket.domain.comment.service;

import com.teamjo.techeermarket.domain.comment.dto.request.CommentRequestDto;
import com.teamjo.techeermarket.domain.comment.entity.Comments;
import com.teamjo.techeermarket.domain.comment.repository.CommentRepository;
import com.teamjo.techeermarket.domain.products.entity.Products;
import com.teamjo.techeermarket.domain.products.repository.ProductRepository;
import com.teamjo.techeermarket.domain.users.entity.Users;
import com.teamjo.techeermarket.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    @Transactional
    public void saveComment(CommentRequestDto commentRequestDto){
        UUID productId = commentRequestDto.getProductUuid();
        UUID userId = commentRequestDto.getUserUuid();
        Products product = productRepository.findByProductUuid(productId);
        Users user = userRepository.findByUserUuid(userId);
        if(product == null){
            log.info("saveComment :: No product :: productId ::  {} ", productId);
        }
        Comments comments = Comments.builder()
                .content(commentRequestDto.getContent())
                .products(product)
                .users(user)
                .build();
        commentRepository.save(comments);

    }


}
