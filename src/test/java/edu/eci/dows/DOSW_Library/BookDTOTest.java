package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.controller.dto.BookDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookDTOTest {

    @Test
    public void testConstructorGettersAndSetters() {
        BookDTO dto = new BookDTO("B1", "Clean Code", "Robert C. Martin", 8, 6);

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
        BookDTO a = new BookDTO("B1", "Clean Code", "Robert C. Martin", 8, 6);
        BookDTO b = new BookDTO("B1", "Clean Code", "Robert C. Martin", 8, 6);
        BookDTO c = new BookDTO("B2", "DDD", "Eric Evans", 10, 10);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
        assertTrue(a.toString().contains("Clean Code"));
    }
}

