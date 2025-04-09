package ru.fedorov.knigoed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fedorov.knigoed.mapper.BookMapper;
import ru.fedorov.knigoed.dto.BookDto;
import ru.fedorov.knigoed.exception.BookNotFoundException;
import ru.fedorov.knigoed.model.Book;
import ru.fedorov.knigoed.repository.BookRepository;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<BookDto> findAllFiltered(String title, String brand, Integer year, Pageable pageable) {
        return bookRepository.findByFilters(title, brand, year, pageable)
                .map(bookMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> findById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto);
    }

    @Override
    @Transactional
    public BookDto save(BookDto bookDto) {
        Objects.requireNonNull(bookDto, "BookDto cannot be null");
        Book book = bookMapper.toEntity(bookDto);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    @Override
    @Transactional
    public BookDto update(Long id, BookDto bookDto) {
        Objects.requireNonNull(bookDto, "BookDto cannot be null");
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        bookMapper.updateBookFromDto(bookDto, existingBook);
        Book updatedBook = bookRepository.save(existingBook);
        return bookMapper.toDto(updatedBook);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }
}