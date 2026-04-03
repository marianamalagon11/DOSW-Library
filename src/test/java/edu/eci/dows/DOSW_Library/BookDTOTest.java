package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.controller.dto.BookDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookDTOTest {

    private BookDTO newBookDTO(String id, String title, String author, int totalStock, int availableStock) {
        return new BookDTO(id, title, author, totalStock, availableStock, null, null, null, null, null, null, null);
    }

    @Test
    public void testConstructorGettersAndSetters() {
        BookDTO dto = newBookDTO("B1", "Clean Code", "Robert C. Martin", 8, 6);

        assertEquals("B1", dto.getId());
        assertEquals("Clean Code", dto.getTitle());
        assertEquals("Robert C. Martin", dto.getAuthor());
        assertEquals(8, dto.getTotalStock());
        assertEquals(6, dto.getAvailableStock());

        dto.setId("B2");
        dto.setTitle("DDD");
        dto.setAuthor("Eric Evans");
        dto.setTotalStock(10);
        dto.setAvailableStock(9);

        assertEquals("B2", dto.getId());
        assertEquals("DDD", dto.getTitle());
        assertEquals("Eric Evans", dto.getAuthor());
        assertEquals(10, dto.getTotalStock());
        assertEquals(9, dto.getAvailableStock());
    }

    @Test
    public void testEqualsHashCodeAndToString() {
        BookDTO a = newBookDTO("B1", "Clean Code", "Robert C. Martin", 8, 6);
        BookDTO b = newBookDTO("B1", "Clean Code", "Robert C. Martin", 8, 6);
        BookDTO c = newBookDTO("B2", "DDD", "Eric Evans", 10, 10);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
        assertTrue(a.toString().contains("Clean Code"));
    }
}

