package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.model.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookModelTest {

    @Test
    public void testGettersAndSetters() {
        Book book = new Book("Title", "Author", "B1", 10, 7);

        book.setTitle("New Title");
        book.setAuthor("New Author");
        book.setId("B2");
        book.setTotalStock(20);
        book.setAvailableStock(15);

        assertEquals("New Title", book.getTitle());
        assertEquals("New Author", book.getAuthor());
        assertEquals("B2", book.getId());
        assertEquals(20, book.getTotalStock());
        assertEquals(15, book.getAvailableStock());
    }

    @Test
    public void testEqualsHashCodeAndToString() {
        Book a = new Book("Title", "Author", "B1", 5, 5);
        Book b = new Book("Title", "Author", "B1", 5, 5);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotNull(a.toString());
        assertTrue(a.toString().contains("Title"));
    }

    @Test
    public void testEqualsWithDifferentBookReturnsFalse() {
        Book a = new Book("Title", "Author", "B1", 5, 5);
        Book b = new Book("Other", "Author", "B1", 5, 5);

        assertNotEquals(a, b);
    }
}

