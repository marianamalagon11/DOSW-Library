package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.model.Loan;
import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.core.validator.LoanValidator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LoanValidatorTest {

    @Test
    public void testIsValidWithCorrectLoanReturnsTrue() {
        Loan loan = new Loan("L1", new Book("1984", "Orwell", "B1", 10, 9), new User("U1", "Maria", "maria", "USER"), LocalDate.now(), "ACTIVE", null);
        assertTrue(LoanValidator.isValid(loan));
    }

    @Test
    public void testIsValidWithNullLoanReturnsFalse() {
        assertFalse(LoanValidator.isValid(null));
    }

    @Test
    public void testIsValidWithMissingRequiredFieldsReturnsFalse() {
        User user = new User("U1", "Maria", "maria", "USER");
        Book book = new Book("1984", "Orwell", "B1", 10, 9);

        assertFalse(LoanValidator.isValid(new Loan("L2", null, user, LocalDate.now(), "ACTIVE", null)));
        assertFalse(LoanValidator.isValid(new Loan("L3", book, null, LocalDate.now(), "ACTIVE", null)));
        assertFalse(LoanValidator.isValid(new Loan("L4", book, user, null, "ACTIVE", null)));
    }
}

