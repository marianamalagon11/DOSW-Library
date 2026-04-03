package edu.eci.dows.tdd.persistence.norelational.adapter;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.persistence.norelational.document.BookAvailabilityDocument;
import edu.eci.dows.tdd.persistence.norelational.document.BookDocument;
import edu.eci.dows.tdd.persistence.norelational.document.BookMetadataDocument;
import edu.eci.dows.tdd.persistence.norelational.repository.BookMongoRepository;
import edu.eci.dows.tdd.persistence.port.BookRepositoryPort;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongo")
public class BookRepositoryMongoAdapter implements BookRepositoryPort {

    private final BookMongoRepository repo;

    public BookRepositoryMongoAdapter(BookMongoRepository repo) {
        this.repo = repo;
    }

    @Override
    public Book save(Book book) {
        BookDocument d = toDocument(book);
        BookDocument saved = repo.save(d);
        return toModel(saved);
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

    private BookDocument toDocument(Book b) {
        BookDocument d = new BookDocument();
        d.setId(b.getId());
        d.setTitle(b.getTitle());
        d.setAuthor(b.getAuthor());

        d.setCategories(b.getCategories());
        d.setPublicationType(b.getPublicationType());
        d.setPublicationDate(b.getPublicationDate());
        d.setIsbn(b.getIsbn());

        // metadata
        if (b.getMetadata() != null) {
            BookMetadataDocument md = new BookMetadataDocument();
            md.setPages(b.getMetadata().getPages());
            md.setLanguage(b.getMetadata().getLanguage());
            md.setCompany(b.getMetadata().getCompany());
            d.setMetadata(md);
        } else {
            d.setMetadata(null);
        }

        Integer total = b.getTotalStock();
        Integer available = b.getAvailableStock();


        if ((total == null || available == null) && b.getAvailability() != null) {
            if (total == null) total = b.getAvailability().getTotalCopies();
            if (available == null) available = b.getAvailability().getAvailableCopies();
        }

        if (total == null) total = 0;
        if (available == null) available = 0;

        int borrowed = Math.max(total - available, 0);
        String status = (available > 0) ? "AVAILABLE" : "UNAVAILABLE";

        BookAvailabilityDocument av = new BookAvailabilityDocument();
        av.setTotalCopies(total);
        av.setAvailableCopies(available);
        av.setBorrowedCopies(borrowed);
        av.setStatus(status);

        d.setAvailability(av);


        d.setAddedAt(b.getAddedAt() != null ? b.getAddedAt() : Instant.now().toString());
        return d;
    }

    private Book toModel(BookDocument d) {
        Book b = new Book();
        b.setId(d.getId());
        b.setTitle(d.getTitle());
        b.setAuthor(d.getAuthor());

        b.setCategories(d.getCategories());
        b.setPublicationType(d.getPublicationType());
        b.setPublicationDate(d.getPublicationDate());
        b.setIsbn(d.getIsbn());
        b.setAddedAt(d.getAddedAt());

        if (d.getMetadata() != null) {
            b.setMetadata(new Book.Metadata(
                    d.getMetadata().getPages(),
                    d.getMetadata().getLanguage(),
                    d.getMetadata().getCompany()
            ));
        }

        if (d.getAvailability() != null) {
            b.setAvailability(new Book.Availability(
                    d.getAvailability().getStatus(),
                    d.getAvailability().getTotalCopies(),
                    d.getAvailability().getAvailableCopies(),
                    d.getAvailability().getBorrowedCopies()
            ));


            b.setTotalStock(d.getAvailability().getTotalCopies() == null ? 0 : d.getAvailability().getTotalCopies());
            b.setAvailableStock(d.getAvailability().getAvailableCopies() == null ? 0 : d.getAvailability().getAvailableCopies());
        } else {
            b.setTotalStock(0);
            b.setAvailableStock(0);
        }

        return b;
    }
}