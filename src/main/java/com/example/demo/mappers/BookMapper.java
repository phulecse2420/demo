package com.example.demo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.demo.data.entities.Book;
import com.example.demo.dto.request.CreateBookDto;
import com.example.demo.dto.request.UpdateBookDto;
import com.example.demo.dto.response.BookDto;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto toDto (Book entity);

    Book toEntity (CreateBookDto dto);

    Book updateEntity (@MappingTarget Book entity, UpdateBookDto dto);
}
