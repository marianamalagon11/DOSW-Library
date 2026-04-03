package edu.eci.dows.tdd.persistence.port;

import edu.eci.dows.tdd.core.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryPort {
    Book save(Book book);
    List<Book> findAll();
    Optional<Book> findById(String id);
    void deleteById(String id);
}