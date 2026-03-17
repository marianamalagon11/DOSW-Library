package edu.eci.dows.tdd.controller.mapper;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.controller.dto.BookDTO;

public class BookMapper {
    public static BookDTO toDTO(Book book) {
        return new BookDTO(book.getId(), book.getTitle(), book.getAuthor());
    }

    public static Book toModel(BookDTO dto) {
        return new Book(dto.getTitle(), dto.getAuthor(), dto.getId());
    }
}
