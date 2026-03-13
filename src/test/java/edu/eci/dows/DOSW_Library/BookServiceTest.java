package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.model.Book;
import edu.eci.dows.tdd.service.BookService;
import org.junit.jupiter.api.Test;

public class BookServiceTest {
    @Test
    public void testAddBook() {
        BookService s = new BookService();
        Book b = new Book("Titulo", "Autor", "ID");
        s.addBook(b, 2);
    }
}
