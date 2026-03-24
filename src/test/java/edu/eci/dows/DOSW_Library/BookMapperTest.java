package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.controller.dto.BookDTO;
import edu.eci.dows.tdd.controller.mapper.BookMapper;
import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.persistence.entity.BookEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookMapperTest {

    @Test
    public void testToDTOMapsAllFields() {
        Book book = new Book("B1", "Clean Code", "Robert C. Martin", 10, 8);

        BookDTO dto = BookMapper.toDTO(book);

        assertEquals("B1", dto.getId());
        assertEquals("Clean Code", dto.getTitle());
        assertEquals("Robert C. Martin", dto.getAuthor());
        assertEquals(10, dto.getTotalStock());
        assertEquals(8, dto.getAvailableStock());
    }

    @Test
    public void testToEntityAndBackMapsAllFields() {
        Book model = new Book("B2", "DDD", "Eric Evans", 12, 11);

        BookEntity entity = BookMapper.toEntity(model);
        Book book = BookMapper.toModel(entity);

        assertEquals("B2", book.getId());
        assertEquals("DDD", book.getTitle());
        assertEquals("Eric Evans", book.getAuthor());
        assertEquals(12, book.getTotalStock());
        assertEquals(11, book.getAvailableStock());
    }
}

