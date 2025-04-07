package com.example.demo.controllers;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.DemoApplication;
import com.example.demo.data.repositories.BookRepository;
import com.example.demo.dto.request.CreateBookDto;
import com.example.demo.dto.response.BookDto;
import com.example.demo.dto.response.GenericResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = DemoApplication.class)
@ActiveProfiles("test")
public class BooksCreateTest {

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    @Autowired
    private BookRepository bookRepository;

    private String baseUrl;

    @BeforeEach
    void setup () {
        baseUrl = "http://localhost:" + port + "/books";
    }

    @Test
    void testCreateBook_shouldReturnCreatedBook () {
        CreateBookDto dto = new CreateBookDto();
        dto.setBookName("The Hobbit");
        dto.setAuthor("J.R.R. Tolkien");
        dto.setPrice(10.0);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateBookDto> requestEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<GenericResponse<BookDto>> response = restTemplate.exchange(
            baseUrl, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<>() {
            }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        var book = response.getBody().getData();
        assertNotNull(book.getId());
        assertEquals("The Hobbit", book.getBookName());
        assertEquals("J.R.R. Tolkien", book.getAuthor());
        assertEquals(10.0, book.getPrice());

        var bookOp = bookRepository.findById(book.getId());
        assertTrue(bookOp.isPresent());
        assertEquals("The Hobbit", bookOp.get().getBookName());
        assertEquals("J.R.R. Tolkien", bookOp.get().getAuthor());
        assertEquals(10.0, bookOp.get().getPrice());
    }

    @Test
    void testCreateBook_InvalidBookName () {
    }

}
