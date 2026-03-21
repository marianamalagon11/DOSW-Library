package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.model.Loan;
import edu.eci.dows.tdd.core.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LoanModelTest {

    @Test
    public void testNoArgsConstructorAndSetters() {
        Loan loan = new Loan("L1", null, null, null, null, null);
        Book book = new Book("1984", "Orwell", "B1");
        User user = new User("Maria", "U1");
        LocalDate loanDate = LocalDate.now();
        LocalDate returnDate = loanDate.plusDays(7);

        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(loanDate);
        loan.setStatus("ACTIVE");
        loan.setReturnDate(returnDate);

        assertEquals(book, loan.getBook());
        assertEquals(user, loan.getUser());
        assertEquals(loanDate, loan.getLoanDate());
        assertEquals("ACTIVE", loan.getStatus());
        assertEquals(returnDate, loan.getReturnDate());
    }

    @Test
    public void testAllArgsConstructorEqualsHashCodeAndToString() {
        Book book = new Book("1984", "Orwell", "B1");
        User user = new User("Maria", "U1");
        LocalDate loanDate = LocalDate.now();

        Loan a = new Loan("L2", book, user, loanDate, "ACTIVE", null);
        Loan b = new Loan("L2", book, user, loanDate, "ACTIVE", null);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotNull(a.toString());
        assertTrue(a.toString().contains("ACTIVE"));
    }

    @Test
    public void testEqualsWithDifferentStatusReturnsFalse() {
        Book book = new Book("1984", "Orwell", "B1");
        User user = new User("Maria", "U1");
        LocalDate loanDate = LocalDate.now();

        Loan a = new Loan("L3", book, user, loanDate, "ACTIVE", null);
        Loan b = new Loan("L3", book, user, loanDate, "RETURNED", null);

        assertNotEquals(a, b);
    }
}

