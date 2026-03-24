package edu.eci.dows.tdd.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Book {
    String id;
    String title;
    String author;
    private int totalStock;
    private int availableStock;

}
