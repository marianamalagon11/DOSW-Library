package edu.eci.dows.tdd.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private String id;
    private String title;
    private String author;

    // MVP
    private int totalStock;
    private int availableStock;

    // para la no relacional
    private List<String> categories;
    private String publicationType;
    private String publicationDate;
    private String isbn;

    private Metadata metadata;
    private Availability availability;

    private String addedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Metadata {
        private Integer pages;
        private String language;
        private String company;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Availability {
        private String status;
        private Integer totalCopies;
        private Integer availableCopies;
        private Integer borrowedCopies;
    }
}