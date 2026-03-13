package edu.eci.dows.tdd.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Loan {
    Book book;
    User user;
    LocalDate loanDate;
    String status;
    LocalDate returnDate;
}
