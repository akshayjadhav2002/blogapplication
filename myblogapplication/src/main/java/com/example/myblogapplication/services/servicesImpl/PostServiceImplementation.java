package com.example.myblogapplication.services.servicesImpl;
import com.example.myblogapplication.entities.Category;
import com.example.myblogapplication.entities.Post;
import com.example.myblogapplication.entities.User;
import com.example.myblogapplication.exceptions.ResourceNotFoundException;
import com.example.myblogapplication.payloads.PostDto;
import com.example.myblogapplication.payloads.PostResponse;
import com.example.myblogapplication.repositories.CategoryRepository;
import com.example.myblogapplication.repositories.PostRepository;
import com.example.myblogapplication.repositories.UserRepository;
import com.example.myblogapplication.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImplementation implements PostService {


    private PostRepository postRepository;
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
     public PostServiceImplementation (PostRepository postRepository , ModelMapper modelMapper){
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto , Integer userId,Integer categoryId) {
        User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","userId",userId));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(
                ()->new ResourceNotFoundException("Category","CategoryId",categoryId));
        Post post = this.PostDtoToPost(postDto);
        post.setPostImage("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost = this.postRepository.save(post);
        return this.PostToPostDto(newPost);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));
        post.setPostTitle(postDto.getPostTitle());
        post.setPostContent(postDto.getContent());
        post.setPostImage(postDto.getImage());
        Post updatedPost = this.postRepository.save(post);
        return this.PostToPostDto(updatedPost);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));
        this.postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize ,String sortBy) {
        //sorting snd pagination
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy).ascending());
        Page<Post> pagePost = this.postRepository.findAll(pageable);
        List<Post> postList = pagePost.getContent();
        // Convert Post entities to PostDto
        List<PostDto> listPost = postList.stream()
                .map(post -> this.PostToPostDto(post))
                .collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(listPost);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalPosts(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }
    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","PostId",postId));
        return this.PostToPostDto(post);
    }

    @Override
    public PostResponse getPostByCategory(Integer categoryId ,Integer pageNumber ,Integer pageSize,String sortBy) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Post> pagePost = this.postRepository.findByCategory(category, pageable);

        List<PostDto> postDtoList = pagePost.getContent().stream()
                .map(this::PostToPostDto)
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtoList);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalPosts(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostResponse getPostByUser(Integer userId,Integer pageNumber,Integer pageSize,String sortBy) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "userId", userId));

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        Page<Post> pagePost = this.postRepository.findByUser(user, pageable);

        List<PostDto> postDtoList = pagePost.getContent().stream()
                .map(this::PostToPostDto)
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtoList);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalPosts(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public List<PostDto> searchPosts(String postTitle) {
        List<PostDto> postList = this.postRepository.findByPostTitleContainingIgnoreCase(postTitle).stream().map(
                (post)-> this.PostToPostDto(post)
        ).collect(Collectors.toList());
         return postList;
    }

    private Post PostDtoToPost (PostDto postDto){
        Post post = this.modelMapper.map(postDto,Post.class) ;
        return post;
    }

    private  PostDto PostToPostDto (Post post){
        PostDto postDto = this.modelMapper.map(post,PostDto.class);
        return postDto;
    }
}
