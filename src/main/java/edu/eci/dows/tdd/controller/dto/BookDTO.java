package edu.eci.dows.tdd.controller.dto;


import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class BookDTO {
    private String id;
    private String title;
    private String author;
    private int totalStock;
    private int availableStock;

    //para no relacional
    private List<String> categories;
    private String publicationType;
    private String publicationDate;
    private String isbn;

    private MetadataDTO metadata;
    private AvailabilityDTO availability;

    private String addedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MetadataDTO {
        private Integer pages;
        private String language;
        private String company;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AvailabilityDTO {
        private String status;
        private Integer totalCopies;
        private Integer availableCopies;
        private Integer borrowedCopies;
    }

}
