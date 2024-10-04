package com.example.myblogapplication.payloads;
import com.example.myblogapplication.entities.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private Integer postId;
    @NotEmpty
    @Size(min = 5 ,message = "Please Enter Title greater than 5 character")
    private String postTitle;
    @NotEmpty
    @Size(min = 10)
    private  String content;
    @NotEmpty
    private String image;
    private Date date;

    private CategoryDto category;
    private UserDto user;
    private Set<CommentDto> comments = new HashSet<>();

}
