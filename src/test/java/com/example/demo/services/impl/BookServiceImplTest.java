package com.example.demo.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.data.entities.Book;
import com.example.demo.data.repositories.BookRepository;
import com.example.demo.dto.request.UpdateBookDto;
import com.example.demo.dto.response.BookDto;
import com.example.demo.exceptions.BookNotFoundException;
import com.example.demo.mappers.BookMapper;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper mapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void updateBook_whenBookExists_shouldUpdateAndReturnDto () {
        Long bookId = 1L;
        UpdateBookDto dto = new UpdateBookDto();
        dto.setBookName("Updated Title");

        Book existingBook = new Book();
        existingBook.setId(bookId);
        existingBook.setBookName("Original Title");

        Book updatedBook = new Book();
        updatedBook.setId(bookId);
        updatedBook.setBookName("Updated Title");

        BookDto expectedDto = BookDto.builder().id(bookId).bookName("Updated Title").build();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(mapper.updateEntity(existingBook, dto)).thenReturn(updatedBook);
        when(bookRepository.save(updatedBook)).thenReturn(updatedBook);
        when(mapper.toDto(updatedBook)).thenReturn(expectedDto);

        BookDto result = bookService.updateBook(bookId, dto);

        assertNotNull(result);
        assertEquals(expectedDto.getId(), result.getId());
        assertEquals(expectedDto.getBookName(), result.getBookName());

        verify(bookRepository, times(1)).findById(bookId);
        verify(mapper, times(1)).updateEntity(existingBook, dto);
        verify(bookRepository, times(1)).save(updatedBook);
        verify(mapper, times(1)).toDto(updatedBook);
    }

    @Test
    void updateBook_whenBookDoesNotExist_shouldThrowException () {
        Long bookId = System.currentTimeMillis();
        UpdateBookDto dto = new UpdateBookDto();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        BookNotFoundException ex = assertThrows(BookNotFoundException.class, () -> bookService.updateBook(bookId, dto));
        assertEquals(1001, ex.getErrorCode());
        assertEquals("Book " + bookId + " is not found", ex.getMessage());

        verify(bookRepository).findById(bookId);
        verifyNoMoreInteractions(mapper, bookRepository);
    }

}
