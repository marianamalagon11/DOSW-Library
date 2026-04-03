package edu.eci.dows.tdd.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor

public class Loan {
    String id;
    Book book;
    User user;
    LocalDate loanDate;
    String status;
    LocalDate returnDate;

    //para la no relacional
    private List<RecordEntry> record;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecordEntry {
        private String status;
        private String date;
    }
}
