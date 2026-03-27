package edu.eci.dows.tdd.persistence.relational.repository;

import edu.eci.dows.tdd.persistence.relational.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, String> { }