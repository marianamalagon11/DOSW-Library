package edu.eci.dows.tdd.core.service;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.persistence.port.BookRepositoryPort;
import edu.eci.dows.tdd.persistence.port.LoanRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepositoryPort bookRepository;
    private final LoanRepositoryPort loanRepository;

    public BookService(BookRepositoryPort bookRepository, LoanRepositoryPort loanRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }

    public Book addBook(Book book) {

        if (book.getAvailability() != null) {
            if (book.getAvailability().getTotalCopies() != null) book.setTotalStock(book.getAvailability().getTotalCopies());
            if (book.getAvailability().getAvailableCopies() != null) book.setAvailableStock(book.getAvailability().getAvailableCopies());
        }
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    public void updateBookStock(String id, int newTotal, int newAvailable) {
        bookRepository.findById(id).ifPresent(existing -> {
            existing.setTotalStock(newTotal);
            existing.setAvailableStock(newAvailable);

            if (existing.getAvailability() != null) {
                existing.getAvailability().setTotalCopies(newTotal);
                existing.getAvailability().setAvailableCopies(newAvailable);
                existing.getAvailability().setBorrowedCopies(Math.max(newTotal - newAvailable, 0));
            }

            bookRepository.save(existing);
        });
    }

    public boolean isBookAvailable(String id) {
        return bookRepository.findById(id)
                .map(b -> b.getAvailableStock() > 0)
                .orElse(false);
    }

    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }

    public Optional<Book> updateBook(String id, Book updatedBook) {
        return bookRepository.findById(id).map(existing -> {
            existing.setTitle(updatedBook.getTitle());
            existing.setAuthor(updatedBook.getAuthor());


            existing.setCategories(updatedBook.getCategories());
            existing.setPublicationType(updatedBook.getPublicationType());
            existing.setPublicationDate(updatedBook.getPublicationDate());
            existing.setIsbn(updatedBook.getIsbn());
            existing.setMetadata(updatedBook.getMetadata());
            existing.setAddedAt(updatedBook.getAddedAt() != null ? updatedBook.getAddedAt() : existing.getAddedAt());


            int newTotal = updatedBook.getTotalStock();
            int oldAvailable = existing.getAvailableStock();
            existing.setTotalStock(newTotal);

            List<String> statusDevuelto = Arrays.asList("DEVUELTO", "RETURNED");
            long prestamosActivos = loanRepository.countActiveByBookId(id);
            int stockCalculado = newTotal - (int) prestamosActivos;
            if (oldAvailable > newTotal) {
                existing.setAvailableStock(Math.max(stockCalculado, 0));
            }


            if (updatedBook.getAvailability() != null) {
                existing.setAvailability(updatedBook.getAvailability());
                if (existing.getAvailability().getTotalCopies() != null) existing.setTotalStock(existing.getAvailability().getTotalCopies());
                if (existing.getAvailability().getAvailableCopies() != null) existing.setAvailableStock(existing.getAvailability().getAvailableCopies());
            } else if (existing.getAvailability() != null) {
                existing.getAvailability().setTotalCopies(existing.getTotalStock());
                existing.getAvailability().setAvailableCopies(existing.getAvailableStock());
                existing.getAvailability().setBorrowedCopies(Math.max(existing.getTotalStock() - existing.getAvailableStock(), 0));
            }

            return bookRepository.save(existing);
        });
    }
}