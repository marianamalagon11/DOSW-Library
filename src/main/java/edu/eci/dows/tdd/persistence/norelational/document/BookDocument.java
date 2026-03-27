package edu.eci.dows.tdd.persistence.norelational.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "books")
@Data
@NoArgsConstructor
public class BookDocument {

    @Id
    private String id;

    private String title;
    private String author;

    private List<String> categories;
    private String publicationType;
    private String publicationDate;
    private String isbn;

    private BookMetadataDocument metadata;
    private BookAvailabilityDocument availability;

    private String addedAt;
}