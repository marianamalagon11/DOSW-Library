package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.model.Loan;
import edu.eci.dows.tdd.core.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LoanModelTest {

    private Book buildBook(String id, String title, String author, int totalStock, int availableStock) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setTotalStock(totalStock);
        book.setAvailableStock(availableStock);
        return book;
    }

    private User buildUser(String id, String name, String username, String password, String role) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }

    @Test
    public void testNoArgsConstructorAndSetters() {
        Loan loan = new Loan("L1", null, null, null, null, null, null);
        Book book = buildBook("B1", "1984", "Orwell", 10, 9);
        User user = buildUser("U1", "Maria", "maria", "secret", "USER");
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
        Book book = buildBook("B1", "1984", "Orwell", 10, 9);
        User user = buildUser("U1", "Maria", "maria", "secret", "USER");
        LocalDate loanDate = LocalDate.now();

        Loan a = new Loan("L2", book, user, loanDate, "ACTIVE", null, null);
        Loan b = new Loan("L2", book, user, loanDate, "ACTIVE", null, null);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotNull(a.toString());
        assertTrue(a.toString().contains("ACTIVE"));
    }

    @Test
    public void testEqualsWithDifferentStatusReturnsFalse() {
        Book book = buildBook("B1", "1984", "Orwell", 10, 9);
        User user = buildUser("U1", "Maria", "maria", "secret", "USER");
        LocalDate loanDate = LocalDate.now();

        Loan a = new Loan("L3", book, user, loanDate, "ACTIVE", null, null);
        Loan b = new Loan("L3", book, user, loanDate, "RETURNED", null, null);

        assertNotEquals(a, b);
    }
}

