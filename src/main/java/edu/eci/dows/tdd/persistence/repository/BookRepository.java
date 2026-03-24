package edu.eci.dows.tdd.persistence.repository;

import edu.eci.dows.tdd.persistence.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, String> { }