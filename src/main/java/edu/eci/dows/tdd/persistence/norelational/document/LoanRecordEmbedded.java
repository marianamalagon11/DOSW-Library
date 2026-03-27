package edu.eci.dows.tdd.persistence.norelational.document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoanRecordEmbedded {
    private String status;
    private String date;
}