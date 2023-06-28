package com.teamjo.techeermarket.domain.comment.controller;

import com.teamjo.techeermarket.domain.comment.dto.request.CommentRequestDto;
import com.teamjo.techeermarket.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;


    @PostMapping
    public void saveComment(@RequestBody CommentRequestDto commentRequestDto){
        commentService.saveComment(commentRequestDto);
    }



}
