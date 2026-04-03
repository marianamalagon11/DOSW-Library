package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.service.BookService;
import edu.eci.dows.tdd.persistence.port.BookRepositoryPort;
import edu.eci.dows.tdd.persistence.port.LoanRepositoryPort;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

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
    public void testAddBookMapsAndReturnsSavedModel() {
        BookRepositoryPort repository = mock(BookRepositoryPort.class);
        LoanRepositoryPort loanRepository = mock(LoanRepositoryPort.class);
        BookService service = new BookService(repository, loanRepository);

        Book input = buildBook("1", "El Quijote", "Cervantes", 3, 2);
        when(repository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Book result = service.addBook(input);

        assertEquals("1", result.getId());
        assertEquals("El Quijote", result.getTitle());
        assertEquals(2, result.getAvailableStock());
    }

    @Test
    public void testGetAllBooksMapsFromRepository() {
        BookRepositoryPort repository = mock(BookRepositoryPort.class);
        LoanRepositoryPort loanRepository = mock(LoanRepositoryPort.class);
        BookService service = new BookService(repository, loanRepository);

        Book b1 = buildBook("1", "A", "AA", 2, 1);
        Book b2 = buildBook("2", "B", "BB", 3, 3);

        when(repository.findAll()).thenReturn(List.of(b1, b2));

        List<Book> result = service.getAllBooks();
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
    }

    @Test
    public void testGetBookByIdWhenFound() {
        BookRepositoryPort repository = mock(BookRepositoryPort.class);
        LoanRepositoryPort loanRepository = mock(LoanRepositoryPort.class);
        BookService service = new BookService(repository, loanRepository);

        Book entity = buildBook("1", "El Quijote", "Cervantes", 3, 2);

        when(repository.findById("1")).thenReturn(Optional.of(entity));

        Optional<Book> result = service.getBookById("1");
        assertTrue(result.isPresent());
        assertEquals("1", result.get().getId());
    }

    @Test
    public void testUpdateBookStockWhenBookExists() {
        BookRepositoryPort repository = mock(BookRepositoryPort.class);
        LoanRepositoryPort loanRepository = mock(LoanRepositoryPort.class);
        BookService service = new BookService(repository, loanRepository);

        Book entity = buildBook("1", "El Quijote", "Cervantes", 3, 2);

        when(repository.findById("1")).thenReturn(Optional.of(entity));

        service.updateBookStock("1", 10, 9);

        assertEquals(10, entity.getTotalStock());
        assertEquals(9, entity.getAvailableStock());
        verify(repository).save(entity);
    }

    @Test
    public void testIsBookAvailableWhenNotFoundReturnsFalse() {
        BookRepositoryPort repository = mock(BookRepositoryPort.class);
        LoanRepositoryPort loanRepository = mock(LoanRepositoryPort.class);
        BookService service = new BookService(repository, loanRepository);

        when(repository.findById("missing")).thenReturn(Optional.empty());

        assertFalse(service.isBookAvailable("missing"));
    }

    @Test
    public void testDeleteBookDelegatesToRepository() {
        BookRepositoryPort repository = mock(BookRepositoryPort.class);
        LoanRepositoryPort loanRepository = mock(LoanRepositoryPort.class);
        BookService service = new BookService(repository, loanRepository);

        service.deleteBook("1");

        verify(repository).deleteById("1");
    }
}
