package edu.eci.dows.tdd.controller.mapper;

import edu.eci.dows.tdd.controller.dto.BookDTO;
import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.persistence.entity.BookEntity;

public class BookMapper {
    public static Book toModel(BookDTO dto) {
        return new Book(
                dto.getId(),
                dto.getTitle(),
                dto.getAuthor(),
                dto.getTotalStock(),
                dto.getAvailableStock()
        );
    }

    public static BookDTO toDTO(Book model) {
        return new BookDTO(
                model.getId(),
                model.getTitle(),
                model.getAuthor(),
                model.getTotalStock(),
                model.getAvailableStock()
        );
    }

    public static Book toModel(BookEntity entity) {
        return new Book(
                entity.getId(),
                entity.getTitle(),
                entity.getAuthor(),
                entity.getTotalStock(),
                entity.getAvailableStock()
        );
    }

}