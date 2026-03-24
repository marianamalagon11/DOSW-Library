package edu.eci.dows.tdd.persistence.repository;

import edu.eci.dows.tdd.persistence.entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<LoanEntity, String> { }