package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.controller.dto.BookDTO;
import edu.eci.dows.tdd.controller.mapper.BookMapper;
import edu.eci.dows.tdd.core.model.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookMapperTest {

    @Test
    public void testToDTOMapsAllFields() {
        Book book = new Book("Clean Code", "Robert C. Martin", "B1");

        BookDTO dto = BookMapper.toDTO(book);

        assertEquals("B1", dto.getId());
        assertEquals("Clean Code", dto.getTitle());
        assertEquals("Robert C. Martin", dto.getAuthor());
    }

    @Test
    public void testToModelMapsAllFields() {
        BookDTO dto = new BookDTO("B2", "DDD", "Eric Evans");

        Book book = BookMapper.toModel(dto);

        assertEquals("B2", book.getId());
        assertEquals("DDD", book.getTitle());
        assertEquals("Eric Evans", book.getAuthor());
    }
}

