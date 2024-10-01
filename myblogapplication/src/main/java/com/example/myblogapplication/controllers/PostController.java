package com.example.myblogapplication.controllers;
import com.example.myblogapplication.config.AppConstants;
import com.example.myblogapplication.payloads.ApiResponse;
import com.example.myblogapplication.payloads.PostDto;
import com.example.myblogapplication.payloads.PostResponse;
import com.example.myblogapplication.services.FileService;
import com.example.myblogapplication.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {

   private final PostService postService;
   private  final FileService fileService;

   @Value("${project.image}")
    private String path;

   //constructor injection
    @Autowired
    PostController (PostService postService,FileService fileService){
        this.postService = postService;
        this.fileService = fileService;
    }

    //create
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
           @Valid @RequestBody PostDto postDto ,
           @PathVariable Integer userId,
           @PathVariable Integer categoryId
            ){
        PostDto newPost = this.postService.createPost(postDto,userId,categoryId);
        return  new ResponseEntity<PostDto>(newPost, HttpStatus.CREATED);
    }

    //getByUser
    @GetMapping("/user/{userId}/posts")
    @Cacheable(value = "PostResponse")
    public ResponseEntity<PostResponse> getPostsByUserId(@PathVariable Integer userId,
       @RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
       @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
       @RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy
    ){
        PostResponse listOfPosts = this.postService.getPostByUser(userId,pageNumber,pageSize,sortBy);
        return new ResponseEntity<PostResponse>(listOfPosts,HttpStatus.OK);
    }

    //getByCategory
    @GetMapping("/category/{categoryId}/posts")
    @Cacheable(value = "PostResponse")
    public ResponseEntity<PostResponse> getPostsByCategoryId(@PathVariable Integer categoryId,
       @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
       @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
       @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy
    ){
        PostResponse postResponse = this.postService.getPostByCategory(categoryId,pageNumber,pageSize,sortBy);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }

    //get All post
    @GetMapping("/posts")
    @Cacheable(value = "PostResponse")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber ,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy
    ){
        PostResponse postResponse = this.postService.getAllPosts(pageNumber,pageSize,sortBy);
        return  new ResponseEntity<>(postResponse ,HttpStatus.OK);
    }

    //getPostByPostId
    @Cacheable(value = "postDto")
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPostByPostId(@PathVariable Integer postId){
        PostDto postDto = this.postService.getPostById(postId);
        return  new ResponseEntity<>(postDto ,HttpStatus.OK);
    }

    //deletePost
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post is Deleted Successfully",true),HttpStatus.OK);
    }

    //updatePost
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost( @Valid @RequestBody PostDto postDto, @PathVariable Integer postId){
        PostDto updatePostDto = this.postService.updatePost(postDto,postId);
        return  new ResponseEntity<PostDto>(updatePostDto,HttpStatus.OK);
    }
    //search
    @GetMapping("/posts/search/{keywords}")
   // @Cacheable(value = "PostDto")
    public  ResponseEntity<List<PostDto>> searchPostByTitle(
            @PathVariable("keywords") String keywords
    ){
        List<PostDto> postDtoList = this.postService.searchPosts(keywords);
        return new ResponseEntity<List<PostDto>>(postDtoList,HttpStatus.OK);
    }

    //uploading images
    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
          @Valid  @RequestParam("image")MultipartFile image,
            @PathVariable Integer postId
            ) throws IOException {
        PostDto postDto = this.postService.getPostById(postId);
        String fileName = this.fileService.uploadImage(path,image);
        postDto.setImage(fileName);
        PostDto updatePostDto = this.postService.updatePost(postDto,postId);
        return new ResponseEntity<>(updatePostDto,HttpStatus.OK);
    }

    //getting images

    @GetMapping(value = "post/image/{imageName}",produces = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE})
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws  IOException{
        InputStream resource = this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
