package com.example.myblogapplication.services.servicesImpl;
import com.example.myblogapplication.entities.Comment;
import com.example.myblogapplication.entities.Post;
import com.example.myblogapplication.exceptions.ResourceNotFoundException;
import com.example.myblogapplication.payloads.CommentDto;
import com.example.myblogapplication.repositories.CommentRepository;
import com.example.myblogapplication.repositories.PostRepository;
import com.example.myblogapplication.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CommentServiceImplementation implements CommentService {

    CommentRepository commentRepository;
    PostRepository postRepository;
    @Autowired
    ModelMapper modelMapper ;
    @Autowired
    public  CommentServiceImplementation (CommentRepository commentRepository,PostRepository postRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        // Fetch the post by ID or throw an exception if not found
        Post post = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
        // Map the DTO to the Comment entity
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        // Associate the comment with the post
        comment.setPost(post);
        // Save the comment to the repository
        Comment savedComment = this.commentRepository.save(comment);
        // Map the saved comment back to the DTO and return it
        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment","commentID",commentId));
        this.commentRepository.delete(comment);
    }
}
