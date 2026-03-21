package edu.eci.dows.tdd.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor

public class Loan {
    String id;
    Book book;
    User user;
    LocalDate loanDate;
    String status;
    LocalDate returnDate;
}
