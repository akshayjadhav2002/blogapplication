package com.example.myblogapplication.repositories;
import com.example.myblogapplication.entities.Category;
import com.example.myblogapplication.entities.Post;
import com.example.myblogapplication.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {
    Page<Post> findByUser(User user, Pageable pageable);
    Page<Post> findByCategory(Category category , Pageable pageable);

    // Search posts by title with case-insensitive search
    List<Post> findByPostTitleContainingIgnoreCase(String postTitle);

}
