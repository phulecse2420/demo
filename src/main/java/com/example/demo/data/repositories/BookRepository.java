package com.example.demo.data.repositories;

import com.example.demo.data.entities.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByBookName(String bookName);

}
