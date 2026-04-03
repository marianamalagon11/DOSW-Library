package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.validator.BookValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookValidatorTest {

    private Book validBook() {
        Book book = new Book();
        book.setId("B1");
        book.setTitle("El Quijote");
        book.setAuthor("Cervantes");
        book.setTotalStock(3);
        book.setAvailableStock(2);
        return book;
    }

    @Test
    public void testIsValidWithCorrectBookReturnsTrue() {
        assertTrue(BookValidator.isValid(validBook()));
    }

    @Test
    public void testIsValidWithNullBookReturnsFalse() {
        assertFalse(BookValidator.isValid(null));
    }

    @Test
    public void testIsValidWithMissingFieldsReturnsFalse() {
        Book missingTitle = validBook();
        missingTitle.setTitle("");
        Book missingAuthor = validBook();
        missingAuthor.setAuthor(null);
        Book missingId = validBook();
        missingId.setId("");
        Book invalidStock = validBook();
        invalidStock.setTotalStock(0);
        invalidStock.setAvailableStock(0);
        Book invalidAvailability = validBook();
        invalidAvailability.setTotalStock(2);
        invalidAvailability.setAvailableStock(3);

        assertFalse(BookValidator.isValid(missingTitle));
        assertFalse(BookValidator.isValid(missingAuthor));
        assertFalse(BookValidator.isValid(missingId));
        assertFalse(BookValidator.isValid(invalidStock));
        assertFalse(BookValidator.isValid(invalidAvailability));
    }
}

