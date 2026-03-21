package edu.eci.dows.tdd.core.service;

import edu.eci.dows.tdd.core.model.Book;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Data
public class BookService {
    private Map<Book, Integer> books = new HashMap<>();

    public void addBook(Book book, int count) {
        books.put(book, count);
    }

    public Set<Book> getAllBooks() {
        return books.keySet();
    }

    public Book getBookById(String id) {
        return books.keySet().stream()
                .filter(b -> b.getId().equals(id))
                .findFirst().orElse(null);
    }

    public void updateBookCount(String id, int newCount) {
        Book book = getBookById(id);
        if (book != null) {
            books.put(book, newCount);
        }
    }

    public boolean isBookAvailable(String id) {
        Book book = getBookById(id);
        return book != null && books.get(book) > 0;
    }

    public void deleteBook(String id) {
        Book book = getBookById(id);
        if (book != null) {
            books.remove(book);
        }
    }
}