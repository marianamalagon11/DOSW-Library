package edu.eci.dows.tdd.controller.dto;


import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class BookDTO {
    private String id;
    private String title;
    private String author;
}
