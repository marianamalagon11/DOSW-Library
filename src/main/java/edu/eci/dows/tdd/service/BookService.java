package edu.eci.dows.tdd.service;

import edu.eci.dows.tdd.model.Book;
import java.util.*;

public class BookService {
    private Map<Book, Integer> books = new HashMap<>();

    public void addBook(Book book, int count) {
        books.put(book, count);
    }
}