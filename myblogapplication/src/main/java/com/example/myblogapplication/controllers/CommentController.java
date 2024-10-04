package com.example.myblogapplication.controllers;

import com.example.myblogapplication.payloads.ApiResponse;
import com.example.myblogapplication.payloads.CommentDto;
import com.example.myblogapplication.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    CommentService commentService;
    @Autowired
    public CommentController (CommentService commentService){
        this.commentService = commentService;
    }
    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentDto> createComment(
             @Valid @RequestBody CommentDto commentDto,
            @PathVariable Integer postId
    ){
        CommentDto commentDto1 = this.commentService.createComment(commentDto,postId);
        return  new ResponseEntity<>(commentDto1 , HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(
            @PathVariable Integer commentId
            ){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Successfully deleted comment",true),HttpStatus.OK);
    }
}
