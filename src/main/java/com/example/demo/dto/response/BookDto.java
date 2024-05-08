package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookDto {

    private Long id;
    private String bookName;
    private String author;
    private Double price;
}
