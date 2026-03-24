package edu.eci.dows.tdd.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
public class BookEntity {
    @Id
    private String id;

    private String title;
    private String author;
    private int totalStock;
    private int availableStock;
}