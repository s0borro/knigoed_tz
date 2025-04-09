package ru.fedorov.knigoed.mapper;

import org.mapstruct.*;
import ru.fedorov.knigoed.dto.BookDto;
import ru.fedorov.knigoed.model.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    Book toEntity(BookDto bookDto);

    BookDto toDto(Book book);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBookFromDto(BookDto bookDto, @MappingTarget Book book);
}