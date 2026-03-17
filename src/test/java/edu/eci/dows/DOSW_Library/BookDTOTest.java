package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.controller.dto.BookDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookDTOTest {

    @Test
    public void testConstructorGettersAndSetters() {
        BookDTO dto = new BookDTO("B1", "Clean Code", "Robert C. Martin");

        assertEquals("B1", dto.getId());
        assertEquals("Clean Code", dto.getTitle());
        assertEquals("Robert C. Martin", dto.getAuthor());

        dto.setId("B2");
        dto.setTitle("DDD");
        dto.setAuthor("Eric Evans");

        assertEquals("B2", dto.getId());
        assertEquals("DDD", dto.getTitle());
        assertEquals("Eric Evans", dto.getAuthor());
    }

    @Test
    public void testEqualsHashCodeAndToString() {
        BookDTO a = new BookDTO("B1", "Clean Code", "Robert C. Martin");
        BookDTO b = new BookDTO("B1", "Clean Code", "Robert C. Martin");
        BookDTO c = new BookDTO("B2", "DDD", "Eric Evans");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
        assertTrue(a.toString().contains("Clean Code"));
    }
}

