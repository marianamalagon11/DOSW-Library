package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.service.BookService;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BookServiceTest {
    @Test
    public void testAddAndGetBook() {
        BookService service = new BookService();
        Book b = new Book("El Quijote", "Cervantes", "1");
        service.addBook(b, 2);
        assertEquals(b, service.getBookById("1"));
        assertTrue(service.isBookAvailable("1"));
    }

    @Test
    public void testUpdateBookCount() {
        BookService service = new BookService();
        Book b = new Book("El Quijote", "Cervantes", "1");
        service.addBook(b, 2);
        service.updateBookCount("1", 0);
        assertFalse(service.isBookAvailable("1"));
    }

    @Test
    public void testGetAllBooks() {
        BookService service = new BookService();
        Book b1 = new Book("El Quijote", "Cervantes", "1");
        Book b2 = new Book("La Odisea", "Homero", "2");
        service.addBook(b1, 1);
        service.addBook(b2, 2);
        assertTrue(service.getAllBooks().contains(b1));
        assertTrue(service.getAllBooks().contains(b2));
    }

    @Test
    public void testGetBookByIdWhenNotFoundReturnsNull() {
        BookService service = new BookService();
        assertNull(service.getBookById("404"));
    }

    @Test
    public void testUpdateBookCountWithUnknownIdDoesNotModifyExistingBooks() {
        BookService service = new BookService();
        Book b = new Book("El Quijote", "Cervantes", "1");
        service.addBook(b, 2);

        service.updateBookCount("unknown", 0);

        assertTrue(service.isBookAvailable("1"));
    }

    @Test
    public void testIsBookAvailableWhenBookDoesNotExist() {
        BookService service = new BookService();
        assertFalse(service.isBookAvailable("missing"));
    }

    @Test
    public void testLombokGetterAndSetterForBooks() {
        BookService service = new BookService();
        Book book = new Book("1984", "Orwell", "B-1984");
        Map<Book, Integer> customBooks = new HashMap<>();
        customBooks.put(book, 5);

        service.setBooks(customBooks);

        assertEquals(customBooks, service.getBooks());
        assertEquals(5, service.getBooks().get(book));
    }

    @Test
    public void testLombokEqualsHashCodeAndToStringForBookService() {
        BookService a = new BookService();
        BookService b = new BookService();

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotNull(a.toString());
        assertTrue(a.toString().contains("books"));
    }
}
