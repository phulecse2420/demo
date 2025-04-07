package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class BookDto {

    Long    id;
    String  bookName;
    String  author;
    Double  price;
    String  createdDate;
    String  updatedDate;
    boolean deleted;
}
