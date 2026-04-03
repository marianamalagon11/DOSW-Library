package edu.eci.dows.tdd.persistence.relational.adapter;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.persistence.port.BookRepositoryPort;
import edu.eci.dows.tdd.persistence.relational.entity.BookEntity;
import edu.eci.dows.tdd.persistence.relational.repository.BookRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("relational")
public class BookRepositoryRelationalAdapter implements BookRepositoryPort {

    private final BookRepository repo;

    public BookRepositoryRelationalAdapter(BookRepository repo) {
        this.repo = repo;
    }

    @Override
    public Book save(Book book) {
        BookEntity e = new BookEntity();
        e.setId(book.getId());
        e.setTitle(book.getTitle());
        e.setAuthor(book.getAuthor());
        e.setTotalStock(book.getTotalStock());
        e.setAvailableStock(book.getAvailableStock());
        BookEntity saved = repo.save(e);


        Book out = new Book();
        out.setId(saved.getId());
        out.setTitle(saved.getTitle());
        out.setAuthor(saved.getAuthor());
        out.setTotalStock(saved.getTotalStock());
        out.setAvailableStock(saved.getAvailableStock());
        return out;
    }

    @Override
    public List<Book> findAll() {
        return repo.findAll().stream().map(this::toModel).toList();
    }

    @Override
    public Optional<Book> findById(String id) {
        return repo.findById(id).map(this::toModel);
    }

    @Override
    public void deleteById(String id) {
        repo.deleteById(id);
    }

    private Book toModel(BookEntity e) {
        Book b = new Book();
        b.setId(e.getId());
        b.setTitle(e.getTitle());
        b.setAuthor(e.getAuthor());
        b.setTotalStock(e.getTotalStock());
        b.setAvailableStock(e.getAvailableStock());
        return b;
    }
}