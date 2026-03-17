package edu.eci.dows.tdd.controller.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class LoanDTO {
    private String bookId;
    private String userId;
    private LocalDate loanDate;
    private String status;
    private LocalDate returnDate;
}
