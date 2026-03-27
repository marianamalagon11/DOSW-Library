package edu.eci.dows.tdd.persistence.relational.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "loan")
@Data
@NoArgsConstructor
public class LoanEntity {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    private LocalDate loanDate;
    private String status;
    private LocalDate returnDate;
}