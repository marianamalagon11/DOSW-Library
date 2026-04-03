package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.controller.dto.LoanDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LoanDTOTest {

    private LoanDTO newLoanDTO(String id, String bookId, String userId, LocalDate loanDate, String status, LocalDate returnDate) {
        return new LoanDTO(id, bookId, userId, loanDate, status, returnDate, null);
    }

    @Test
    public void testConstructorGettersAndSetters() {
        LoanDTO dto = newLoanDTO("L1", "B1", "U1", LocalDate.of(2026, 3, 1), "ACTIVE", null);

        assertEquals("B1", dto.getBookId());
        assertEquals("U1", dto.getUserId());
        assertEquals(LocalDate.of(2026, 3, 1), dto.getLoanDate());
        assertEquals("ACTIVE", dto.getStatus());
        assertNull(dto.getReturnDate());

        dto.setBookId("B2");
        dto.setUserId("U2");
        dto.setLoanDate(LocalDate.of(2026, 3, 2));
        dto.setStatus("RETURNED");
        dto.setReturnDate(LocalDate.of(2026, 3, 10));

        assertEquals("B2", dto.getBookId());
        assertEquals("U2", dto.getUserId());
        assertEquals(LocalDate.of(2026, 3, 2), dto.getLoanDate());
        assertEquals("RETURNED", dto.getStatus());
        assertEquals(LocalDate.of(2026, 3, 10), dto.getReturnDate());
    }

    @Test
    public void testEqualsHashCodeAndToString() {
        LoanDTO a = newLoanDTO("L1", "B1", "U1", LocalDate.of(2026, 3, 1), "ACTIVE", null);
        LoanDTO b = newLoanDTO("L1", "B1", "U1", LocalDate.of(2026, 3, 1), "ACTIVE", null);
        LoanDTO c = newLoanDTO("L2", "B2", "U2", LocalDate.of(2026, 3, 2), "RETURNED", LocalDate.of(2026, 3, 10));

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
        assertTrue(a.toString().contains("ACTIVE"));
    }
}

