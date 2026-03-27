package edu.eci.dows.tdd.persistence.norelational.repository;

import edu.eci.dows.tdd.persistence.norelational.document.BookDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookMongoRepository extends MongoRepository<BookDocument, String> {

    Optional<BookDocument> findByIsbn(String isbn);

    List<BookDocument> findByAuthor(String author);

    List<BookDocument> findByCategoriesContaining(String category);
}