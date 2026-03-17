package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.validator.BookValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookValidatorTest {

    @Test
    public void testIsValidWithCorrectBookReturnsTrue() {
        assertTrue(BookValidator.isValid(new Book("El Quijote", "Cervantes", "B1")));
    }

    @Test
    public void testIsValidWithNullBookReturnsFalse() {
        assertFalse(BookValidator.isValid(null));
    }

    @Test
    public void testIsValidWithMissingFieldsReturnsFalse() {
        assertFalse(BookValidator.isValid(new Book("", "Cervantes", "B1")));
        assertFalse(BookValidator.isValid(new Book("El Quijote", null, "B1")));
        assertFalse(BookValidator.isValid(new Book("El Quijote", "Cervantes", "")));
    }
}

