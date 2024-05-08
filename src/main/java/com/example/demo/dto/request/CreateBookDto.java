package com.example.demo.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateBookDto {

    @NotBlank(message = "Book name is required")
    private String bookName;
    private String author;
    @Min(value = 1, message = "Price should be a positive number")
    @NotNull(message = "Price is required")
    private Double price;

}
