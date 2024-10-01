package com.example.myblogapplication.services;
import com.example.myblogapplication.payloads.PostDto;
import com.example.myblogapplication.payloads.PostResponse;

import java.util.List;

public interface PostService {

    // Create a new post
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    // Update an existing post
    PostDto updatePost(PostDto postDto, Integer postId);

    // Delete a post by ID
    void deletePost(Integer postId);

    // Get all posts with pagination and sorting
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy);

    // Get a post by its ID
    PostDto getPostById(Integer postId);

    // Get all posts by category with pagination and sorting
    PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy);

    // Get all posts by a user with pagination and sorting
    PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy);

    // Search for posts by title
    List<PostDto> searchPosts(String postTitle);
}
