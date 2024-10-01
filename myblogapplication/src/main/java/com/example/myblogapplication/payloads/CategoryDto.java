package com.example.myblogapplication.payloads;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private Integer Id;
    @NotEmpty
    @Size(min = 4,message = "Minimum Size of Category Name is 4 Character")
    private  String categoryName;
    @NotEmpty
    @Size(min=10,message = "Minimum Size of Category Description is 10 Character")
    private String categoryDescription;
}