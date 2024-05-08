package com.example.demo.controllers;

import com.example.demo.data.entities.Book;
import com.example.demo.dto.request.CreateBookDto;
import com.example.demo.dto.request.UpdateBookDto;
import com.example.demo.dto.response.BookDto;
import com.example.demo.dto.response.ErrorResponse;
import com.example.demo.exceptions.BookNotFoundException;
import com.example.demo.services.BookService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BooksController {

    private BookService bookService;

    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookService.getAllBooks();
    }

    @ExceptionHandler({BookNotFoundException.class})
    protected ResponseEntity<ErrorResponse> handleBookNotFoundException(BookNotFoundException exception) {
        var error = ErrorResponse.builder().code(HttpStatus.NOT_FOUND.value()).message("Book Not Found").build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable("id") Long id) {
        return bookService.getBook(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@Valid @RequestBody CreateBookDto dto) {
        return bookService.createBook(dto);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable("id") Long id, @Valid @RequestBody UpdateBookDto dto) {
        return bookService.updateBook(id, dto);
    }
}
