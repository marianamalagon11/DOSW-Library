package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.model.Loan;
import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.core.service.LoanService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LoanServiceTest {
    @Test
    public void testAddLoanAndGetLoanByUser() {
        LoanService s = new LoanService();
        User u = new User("Luis", "U005");
        Book b = new Book("Cien años", "Gabo", "CA1");
        Loan l = new Loan("L1", b, u, LocalDate.now(), "ACTIVE", null);
        s.addLoan(l);
        assertEquals(1, s.getLoansByUser(u).size());
    }

    @Test
    public void testGetAllLoans() {
        LoanService s = new LoanService();
        User u1 = new User("Carlos", "U006");
        User u2 = new User("Sonia", "U007");
        Book b1 = new Book("Rayuela", "Cortázar", "R1");
        Book b2 = new Book("La Ciudad", "Arias", "LC1");
        Loan l1 = new Loan("L2", b1, u1, LocalDate.now(), "ACTIVE", null);
        Loan l2 = new Loan("L3", b2, u2, LocalDate.now(), "ACTIVE", null);
        s.addLoan(l1);
        s.addLoan(l2);
        assertEquals(2, s.getAllLoans().size());
    }

    @Test
    public void testGetLoansByBookReturnsOnlyMatchingLoans() {
        LoanService s = new LoanService();
        User u = new User("Carlos", "U006");
        Book targetBook = new Book("Rayuela", "Cortazar", "R1");
        Book otherBook = new Book("La Ciudad", "Arias", "LC1");
        Loan l1 = new Loan("L4", targetBook, u, LocalDate.now(), "ACTIVE", null);
        Loan l2 = new Loan("L5", otherBook, u, LocalDate.now(), "ACTIVE", null);
        s.addLoan(l1);
        s.addLoan(l2);

        assertEquals(1, s.getLoansByBook(targetBook).size());
        assertTrue(s.getLoansByBook(targetBook).contains(l1));
    }

    @Test
    public void testGetLoansByBookWhenNoMatchesReturnsEmptyList() {
        LoanService s = new LoanService();
        User u = new User("Carlos", "U006");
        Book existingBook = new Book("Rayuela", "Cortazar", "R1");
        s.addLoan(new Loan("L6", existingBook, u, LocalDate.now(), "ACTIVE", null));

        assertTrue(s.getLoansByBook(new Book("Otro", "Autor", "X")).isEmpty());
    }

    @Test
    public void testGetLoansByUserWhenNoMatchesReturnsEmptyList() {
        LoanService s = new LoanService();
        User requestedUser = new User("NoMatch", "U404");
        User existingUser = new User("Carlos", "U006");
        Book existingBook = new Book("Rayuela", "Cortazar", "R1");
        s.addLoan(new Loan("L7", existingBook, existingUser, LocalDate.now(), "ACTIVE", null));

        assertTrue(s.getLoansByUser(requestedUser).isEmpty());
    }

    @Test
    public void testLombokGetterAndSetterForLoans() {
        LoanService service = new LoanService();
        List<Loan> customLoans = new ArrayList<>();
        customLoans.add(new Loan("L8", new Book("1984", "Orwell", "B1"), new User("Ana", "U10"), LocalDate.now(), "ACTIVE", null));

        service.setLoans(customLoans);

        assertEquals(customLoans, service.getLoans());
        assertEquals(1, service.getLoans().size());
    }

    @Test
    public void testLombokEqualsHashCodeAndToStringForLoanService() {
        LoanService a = new LoanService();
        LoanService b = new LoanService();

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotNull(a.toString());
        assertTrue(a.toString().contains("loans"));
    }
}