package edu.eci.dows.tdd.controller.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class LoanDTO {
    private String id;
    private String bookId;
    private String userId;
    private LocalDate loanDate;
    private String status;
    private LocalDate returnDate;

    //para la no relacional
    private List<RecordDTO> record;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecordDTO {
        private String status;
        private String date;
    }
}