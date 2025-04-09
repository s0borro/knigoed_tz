package ru.fedorov.knigoed.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.fedorov.knigoed.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE " +
            "(:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:brand IS NULL OR LOWER(b.brand) LIKE LOWER(CONCAT('%', :brand, '%'))) AND " +
            "(:year IS NULL OR b.year = :year)")
    Page<Book> findByFilters(
            @Param("title") String title,
            @Param("brand") String brand,
            @Param("year") Integer year,
            Pageable pageable);
}