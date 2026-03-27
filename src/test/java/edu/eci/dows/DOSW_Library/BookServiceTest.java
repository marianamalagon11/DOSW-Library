package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.service.BookService;
import edu.eci.dows.tdd.persistence.relational.entity.BookEntity;
import edu.eci.dows.tdd.persistence.relational.repository.BookRepository;
import edu.eci.dows.tdd.persistence.relational.repository.LoanRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {
    @Test
    public void testAddBookMapsAndReturnsSavedModel() {
        BookRepository repository = mock(BookRepository.class);
        LoanRepository loanRepository = mock(LoanRepository.class);
        BookService service = new BookService(repository, loanRepository);

        Book input = new Book("El Quijote", "Cervantes", "1", 3, 2);
        BookEntity saved = new BookEntity();
        saved.setId("1");
        saved.setTitle("El Quijote");
        saved.setAuthor("Cervantes");
        saved.setTotalStock(3);
        saved.setAvailableStock(2);

        when(repository.save(any(BookEntity.class))).thenReturn(saved);

        Book result = service.addBook(input);

        assertEquals("1", result.getId());
        assertEquals("El Quijote", result.getTitle());
        assertEquals(2, result.getAvailableStock());
    }

    @Test
    public void testGetAllBooksMapsFromRepository() {
        BookRepository repository = mock(BookRepository.class);
        LoanRepository loanRepository = mock(LoanRepository.class);
        BookService service = new BookService(repository, loanRepository);

        BookEntity b1 = new BookEntity();
        b1.setId("1");
        b1.setTitle("A");
        b1.setAuthor("AA");
        b1.setTotalStock(2);
        b1.setAvailableStock(1);

        BookEntity b2 = new BookEntity();
        b2.setId("2");
        b2.setTitle("B");
        b2.setAuthor("BB");
        b2.setTotalStock(3);
        b2.setAvailableStock(3);

        when(repository.findAll()).thenReturn(List.of(b1, b2));

        List<Book> result = service.getAllBooks();
        assertEquals(2, result.size());
        assertEquals("1", result.get(0).getId());
    }

    @Test
    public void testGetBookByIdWhenFound() {
        BookRepository repository = mock(BookRepository.class);
        LoanRepository loanRepository = mock(LoanRepository.class);
        BookService service = new BookService(repository, loanRepository);

        BookEntity entity = new BookEntity();
        entity.setId("1");
        entity.setTitle("El Quijote");
        entity.setAuthor("Cervantes");
        entity.setTotalStock(3);
        entity.setAvailableStock(2);

        when(repository.findById("1")).thenReturn(Optional.of(entity));

        Optional<Book> result = service.getBookById("1");
        assertTrue(result.isPresent());
        assertEquals("1", result.get().getId());
    }

    @Test
    public void testUpdateBookStockWhenBookExists() {
        BookRepository repository = mock(BookRepository.class);
        LoanRepository loanRepository = mock(LoanRepository.class);
        BookService service = new BookService(repository, loanRepository);

        BookEntity entity = new BookEntity();
        entity.setId("1");
        entity.setTotalStock(3);
        entity.setAvailableStock(2);

        when(repository.findById("1")).thenReturn(Optional.of(entity));

        service.updateBookStock("1", 10, 9);

        assertEquals(10, entity.getTotalStock());
        assertEquals(9, entity.getAvailableStock());
        verify(repository).save(entity);
    }

    @Test
    public void testIsBookAvailableWhenNotFoundReturnsFalse() {
        BookRepository repository = mock(BookRepository.class);
        LoanRepository loanRepository = mock(LoanRepository.class);
        BookService service = new BookService(repository, loanRepository);

        when(repository.findById("missing")).thenReturn(Optional.empty());

        assertFalse(service.isBookAvailable("missing"));
    }

    @Test
    public void testDeleteBookDelegatesToRepository() {
        BookRepository repository = mock(BookRepository.class);
        LoanRepository loanRepository = mock(LoanRepository.class);
        BookService service = new BookService(repository, loanRepository);

        service.deleteBook("1");

        verify(repository).deleteById("1");
    }
}
