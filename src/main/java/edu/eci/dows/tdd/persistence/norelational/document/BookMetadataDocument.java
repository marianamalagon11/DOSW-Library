package edu.eci.dows.tdd.persistence.norelational.document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookMetadataDocument {
    private Integer pages;
    private String language;
    private String company;
}