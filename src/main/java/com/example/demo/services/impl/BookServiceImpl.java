package com.example.demo.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.data.entities.Book;
import com.example.demo.data.repositories.BookRepository;
import com.example.demo.dto.request.CreateBookDto;
import com.example.demo.dto.request.UpdateBookDto;
import com.example.demo.dto.response.BookDto;
import com.example.demo.exceptions.BookNotFoundException;
import com.example.demo.mappers.BookMapper;
import com.example.demo.services.BookService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private BookMapper     mapper;

    public BookServiceImpl (BookRepository bookRepository, BookMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    @Override
    public BookDto getBook (Long id) {
        return bookRepository.findById(id)
                             .map(mapper::toDto)
                             .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    public Page<BookDto> getBooks (Pageable pageable) {
        return bookRepository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    @Transactional
    public BookDto createBook (CreateBookDto dto) {
        Book book = mapper.toEntity(dto);
        book = bookRepository.save(book);
        return mapper.toDto(book);
    }

    @Override
    @Transactional
    public BookDto updateBook (Long id, UpdateBookDto dto) {
        return bookRepository.findById(id)
                             .map(book -> {
                                 var updatedBook = mapper.updateEntity(book, dto);
                                 updatedBook = bookRepository.save(updatedBook);
                                 return mapper.toDto(updatedBook);
                             })
                             .orElseThrow(() -> new BookNotFoundException(id));
    }
}
