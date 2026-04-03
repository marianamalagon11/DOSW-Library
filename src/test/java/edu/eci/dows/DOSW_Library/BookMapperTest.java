package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.controller.dto.BookDTO;
import edu.eci.dows.tdd.controller.mapper.BookMapper;
import edu.eci.dows.tdd.core.model.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookMapperTest {

    private Book buildBook(String id, String title, String author, int totalStock, int availableStock) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setTotalStock(totalStock);
        book.setAvailableStock(availableStock);
        return book;
    }

    @Test
    public void testToDTOMapsAllFields() {
        Book book = buildBook("B1", "Clean Code", "Robert C. Martin", 10, 8);

        BookDTO dto = BookMapper.toDTO(book);

        assertEquals("B1", dto.getId());
        assertEquals("Clean Code", dto.getTitle());
        assertEquals("Robert C. Martin", dto.getAuthor());
        assertEquals(10, dto.getTotalStock());
        assertEquals(8, dto.getAvailableStock());
    }

    @Test
    public void testToModelMapsAllFields() {
        BookDTO dto = new BookDTO("B2", "DDD", "Eric Evans", 12, 11, null, null, null, null, null, null, null);
        Book book = BookMapper.toModel(dto);

        assertEquals("B2", book.getId());
        assertEquals("DDD", book.getTitle());
        assertEquals("Eric Evans", book.getAuthor());
        assertEquals(12, book.getTotalStock());
        assertEquals(11, book.getAvailableStock());
    }
}

