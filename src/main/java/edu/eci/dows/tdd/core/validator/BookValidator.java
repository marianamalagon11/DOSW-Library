package edu.eci.dows.tdd.core.validator;

import edu.eci.dows.tdd.core.model.Book;

public class BookValidator {
    public static boolean isValid(Book book) {
        return book != null
                && book.getId() != null && !book.getId().isEmpty()
                && book.getTitle() != null && !book.getTitle().isEmpty()
                && book.getAuthor() != null && !book.getAuthor().isEmpty()
                && book.getTotalStock() > 0
                && book.getAvailableStock() >= 0
                && book.getAvailableStock() <= book.getTotalStock();
    }
}