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
    public void testToDTOMapsAllFields() {
        Loan loan = new Loan(
                "L1",
                buildBook("B1", "1984", "Orwell", 10, 9),
                buildUser("U1", "Maria", "maria", "secret", "USER"),
                LocalDate.of(2026, 3, 1),
                "ACTIVE",
                LocalDate.of(2026, 3, 15),
                null
        );

        LoanDTO dto = LoanMapper.toDTO(loan);

        assertEquals("L1", dto.getId());
        assertEquals("B1", dto.getBookId());
        assertEquals("U1", dto.getUserId());
        assertEquals(LocalDate.of(2026, 3, 1), dto.getLoanDate());
        assertEquals("ACTIVE", dto.getStatus());
        assertEquals(LocalDate.of(2026, 3, 15), dto.getReturnDate());
    }

    @Test
    public void testToModelMapsAllFields() {
        LoanDTO dto = new LoanDTO(
                "L2",
                "B2",
                "U2",
                LocalDate.of(2026, 4, 1),
                "RETURNED",
                LocalDate.of(2026, 4, 10),
                null
        );
        Book book = buildBook("B2", "DDD", "Evans", 7, 6);
        User user = buildUser("U2", "Ana", "ana", "secret", "USER");

        Loan loan = LoanMapper.toModel(dto, book, user);

        assertEquals("L2", loan.getId());
        assertEquals(book, loan.getBook());
        assertEquals(user, loan.getUser());
        assertEquals(LocalDate.of(2026, 4, 1), loan.getLoanDate());
        assertEquals("RETURNED", loan.getStatus());
        assertEquals(LocalDate.of(2026, 4, 10), loan.getReturnDate());
    }
}

