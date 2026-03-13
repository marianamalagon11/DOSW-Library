package edu.eci.dows.tdd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Loan {
    Book book;
    User user;
    LocalDate loanDate;
    String status;
    LocalDate returnDate;
}
