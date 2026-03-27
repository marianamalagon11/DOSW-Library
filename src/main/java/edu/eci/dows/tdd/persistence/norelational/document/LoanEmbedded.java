package edu.eci.dows.tdd.persistence.norelational.document;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LoanEmbedded {
    private String id;
    private String bookId;
    private String loanDate;
    private String status;
    private String returnDate;
    private List<LoanRecordEmbedded> record;
}