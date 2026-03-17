package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.controller.dto.LoanDTO;
import edu.eci.dows.tdd.controller.mapper.LoanMapper;
import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.model.Loan;
import edu.eci.dows.tdd.core.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LoanMapperTest {

    @Test
    public void testToDTOMapsAllFields() {
        Loan loan = new Loan(
                new Book("1984", "Orwell", "B1"),
                new User("Maria", "U1"),
                LocalDate.of(2026, 3, 1),
                "ACTIVE",
                LocalDate.of(2026, 3, 15)
        );

        LoanDTO dto = LoanMapper.toDTO(loan);

        assertEquals("B1", dto.getBookId());
        assertEquals("U1", dto.getUserId());
        assertEquals(LocalDate.of(2026, 3, 1), dto.getLoanDate());
        assertEquals("ACTIVE", dto.getStatus());
        assertEquals(LocalDate.of(2026, 3, 15), dto.getReturnDate());
    }

    @Test
    public void testToModelMapsAllFields() {
        LoanDTO dto = new LoanDTO(
                "B2",
                "U2",
                LocalDate.of(2026, 4, 1),
                "RETURNED",
                LocalDate.of(2026, 4, 10)
        );
        Book book = new Book("DDD", "Evans", "B2");
        User user = new User("Ana", "U2");

        Loan loan = LoanMapper.toModel(dto, book, user);

        assertEquals(book, loan.getBook());
        assertEquals(user, loan.getUser());
        assertEquals(LocalDate.of(2026, 4, 1), loan.getLoanDate());
        assertEquals("RETURNED", loan.getStatus());
        assertEquals(LocalDate.of(2026, 4, 10), loan.getReturnDate());
    }
}

