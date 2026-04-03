package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.model.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookModelTest {

    @Test
    public void testGettersAndSetters() {
        Book book = new Book();

        book.setId("B1");
        book.setTitle("New Title");
        book.setAuthor("New Author");
        book.setTotalStock(20);
        book.setAvailableStock(15);

        assertEquals("New Title", book.getTitle());
        assertEquals("New Author", book.getAuthor());
        assertEquals("B1", book.getId());
        assertEquals(20, book.getTotalStock());
        assertEquals(15, book.getAvailableStock());
    }

    @Test
    public void testEqualsHashCodeAndToString() {
        Book a = new Book();
        a.setId("B1");
        a.setTitle("Title");
        a.setAuthor("Author");
        a.setTotalStock(5);
        a.setAvailableStock(5);

        Book b = new Book();
        b.setId("B1");
        b.setTitle("Title");
        b.setAuthor("Author");
        b.setTotalStock(5);
        b.setAvailableStock(5);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotNull(a.toString());
        assertTrue(a.toString().contains("Title"));
    }

    @Test
    public void testEqualsWithDifferentBookReturnsFalse() {
        Book a = new Book();
        a.setId("B1");
        a.setTitle("Title");
        a.setAuthor("Author");
        a.setTotalStock(5);
        a.setAvailableStock(5);

        Book b = new Book();
        b.setId("B1");
        b.setTitle("Other");
        b.setAuthor("Author");
        b.setTotalStock(5);
        b.setAvailableStock(5);

        assertNotEquals(a, b);
    }
}

