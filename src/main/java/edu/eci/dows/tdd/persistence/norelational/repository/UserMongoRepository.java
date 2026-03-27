package edu.eci.dows.tdd.persistence.norelational.repository;

import edu.eci.dows.tdd.persistence.nonrelational.document.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserMongoRepository extends MongoRepository<UserDocument, String> {
    Optional<UserDocument> findByUsername(String username);
}