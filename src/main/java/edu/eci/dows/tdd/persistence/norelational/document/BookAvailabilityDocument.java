package edu.eci.dows.tdd.persistence.norelational.document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookAvailabilityDocument {
    private String status;
    private Integer totalCopies;
    private Integer availableCopies;
    private Integer borrowedCopies;
}