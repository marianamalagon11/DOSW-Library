package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.model.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookModelTest {

    @Test
    public void testGettersAndSetters() {
        Book book = new Book("Title", "Author", "B1");

        book.setTitle("New Title");
        book.setAuthor("New Author");
        book.setId("B2");

        assertEquals("New Title", book.getTitle());
        assertEquals("New Author", book.getAuthor());
        assertEquals("B2", book.getId());
    }

    @Test
    public void testEqualsHashCodeAndToString() {
        Book a = new Book("Title", "Author", "B1");
        Book b = new Book("Title", "Author", "B1");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotNull(a.toString());
        assertTrue(a.toString().contains("Title"));
    }

    @Test
    public void testEqualsWithDifferentBookReturnsFalse() {
        Book a = new Book("Title", "Author", "B1");
        Book b = new Book("Other", "Author", "B1");

        assertNotEquals(a, b);
    }
}

