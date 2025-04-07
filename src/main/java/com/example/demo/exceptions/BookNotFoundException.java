package com.example.demo.exceptions;

import com.example.demo.constants.ErrorCode;

public class BookNotFoundException extends ResourceNotFoundException {

    public BookNotFoundException (Long id) {
        super(ErrorCode.BOOK_NOT_FOUND, "Book " + id + " is not found");
    }
}
