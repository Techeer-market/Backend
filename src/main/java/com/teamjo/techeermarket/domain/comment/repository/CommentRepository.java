package com.teamjo.techeermarket.domain.comment.repository;

import com.teamjo.techeermarket.domain.comment.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comments, Long> {

    Comments findByCommentUuid(UUID commentUuid);
}
