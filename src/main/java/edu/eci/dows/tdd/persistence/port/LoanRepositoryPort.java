package edu.eci.dows.tdd.persistence.port;

import edu.eci.dows.tdd.core.model.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanRepositoryPort {
    Loan save(Loan loan);
    List<Loan> findAll();
    Optional<Loan> findById(String id);
    void deleteById(String id);

    long countActiveByBookId(String bookId);
    List<Loan> findAllByUserId(String userId);
    boolean existsByIdAndUserId(String id, String userId);
}