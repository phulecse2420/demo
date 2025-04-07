package com.example.demo.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.dto.request.CreateBookDto;
import com.example.demo.dto.request.UpdateBookDto;
import com.example.demo.dto.response.BookDto;

public interface BookService {

    Page<BookDto> getBooks (Pageable pageable);

    BookDto getBook(Long id);

    BookDto createBook(CreateBookDto dto);

    BookDto updateBook(Long id, UpdateBookDto dto);
}
