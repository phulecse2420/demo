package com.example.demo.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.data.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByBookName(String bookName);

}
