package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.model.Loan;
import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.core.validator.LoanValidator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LoanValidatorTest {

    private Book buildBook() {
        Book book = new Book();
        book.setId("B1");
        book.setTitle("1984");
        book.setAuthor("Orwell");
        book.setTotalStock(10);
        book.setAvailableStock(9);
        return book;
    }

    private User buildUser() {
        User user = new User();
        user.setId("U1");
        user.setName("Maria");
        user.setUsername("maria");
        user.setPassword("secret");
        user.setRole("USER");
        return user;
    }

    @Test
    public void testIsValidWithCorrectLoanReturnsTrue() {
        Loan loan = new Loan("L1", buildBook(), buildUser(), LocalDate.now(), "ACTIVE", null, null);
        assertTrue(LoanValidator.isValid(loan));
    }

    @Test
    public void testIsValidWithNullLoanReturnsFalse() {
        assertFalse(LoanValidator.isValid(null));
    }

    @Test
    public void testIsValidWithMissingRequiredFieldsReturnsFalse() {
        User user = buildUser();
        Book book = buildBook();

        assertFalse(LoanValidator.isValid(new Loan("L2", null, user, LocalDate.now(), "ACTIVE", null, null)));
        assertFalse(LoanValidator.isValid(new Loan("L3", book, null, LocalDate.now(), "ACTIVE", null, null)));
        assertFalse(LoanValidator.isValid(new Loan("L4", book, user, null, "ACTIVE", null, null)));
    }
}

