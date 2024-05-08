package com.example.demo.services;

import com.example.demo.data.entities.Book;
import com.example.demo.dto.request.CreateBookDto;
import com.example.demo.dto.request.UpdateBookDto;
import com.example.demo.dto.response.BookDto;
import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    BookDto getBook(Long id);

    BookDto createBook(CreateBookDto dto);

    BookDto updateBook(Long id, UpdateBookDto dto);
}
