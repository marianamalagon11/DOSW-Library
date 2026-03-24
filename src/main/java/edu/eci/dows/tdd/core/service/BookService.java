package edu.eci.dows.tdd.core.service;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.persistence.repository.BookRepository;
import edu.eci.dows.tdd.persistence.entity.BookEntity;
import edu.eci.dows.tdd.controller.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) {
        BookEntity entity = BookMapper.toEntity(book);
        BookEntity saved = bookRepository.save(entity);
        return BookMapper.toModel(saved);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll().stream().map(BookMapper::toModel).toList();
    }

    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id).map(BookMapper::toModel);
    }

    public void updateBookStock(String id, int newTotal, int newAvailable) {
        bookRepository.findById(id).ifPresent(entity -> {
            entity.setTotalStock(newTotal);
            entity.setAvailableStock(newAvailable);
            bookRepository.save(entity);
        });
    }

    public boolean isBookAvailable(String id) {
        return bookRepository.findById(id)
                .map(entity -> entity.getAvailableStock() > 0)
                .orElse(false);
    }

    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }
}