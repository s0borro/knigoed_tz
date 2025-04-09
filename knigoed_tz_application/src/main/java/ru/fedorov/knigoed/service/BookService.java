package ru.fedorov.knigoed.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.fedorov.knigoed.dto.BookDto;

import java.util.Optional;

public interface BookService {

    Page<BookDto> findAllFiltered(String title, String brand, Integer year, Pageable pageable);

    Optional<BookDto> findById(Long id);

    BookDto save(BookDto bookDto);

    BookDto update(Long id, BookDto bookDto);

    void deleteById(Long id);
}