package edu.eci.dows.tdd.persistence.relational.repository;

import edu.eci.dows.tdd.persistence.relational.entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<LoanEntity, String> {
    long countByBookIdAndStatusNotIn(String bookId, List<String> statuses);

    List<LoanEntity> findAllByUser_Id(String userId);

    boolean existsByIdAndUser_Id(String id, String userId);
}