package com.example.demo.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.CreateBookDto;
import com.example.demo.dto.request.UpdateBookDto;
import com.example.demo.dto.response.BookDto;
import com.example.demo.dto.response.GenericResponse;
import com.example.demo.services.BookService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;

    public BooksController (BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<GenericResponse<Page<BookDto>>> getBooks (
        @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return ResponseEntity.ok(GenericResponse.<Page<BookDto>>builder()
                                     .data(bookService.getBooks(pageable))
                                     .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<BookDto>> getBook (@PathVariable("id") Long id) {
        return ResponseEntity.ok(GenericResponse.<BookDto>builder()
                                     .data(bookService.getBook(id))
                                     .build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GenericResponse<BookDto>> createBook (@Valid @RequestBody CreateBookDto dto) {
        return ResponseEntity.ok(GenericResponse.<BookDto>builder()
                                     .data(bookService.createBook(dto))
                                     .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<BookDto>> updateBook (@PathVariable("id") Long id,
                                                                @Valid @RequestBody UpdateBookDto dto) {
        return ResponseEntity.ok(GenericResponse.<BookDto>builder()
                                     .data(bookService.updateBook(id, dto))
                                     .build());
    }
}
