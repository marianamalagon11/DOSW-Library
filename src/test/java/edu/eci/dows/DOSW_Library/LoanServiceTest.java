package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.model.*;
import edu.eci.dows.tdd.service.LoanService;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class LoanServiceTest {
    @Test
    public void testAddLoan() {
        LoanService s = new LoanService();
        User u = new User("Nombre", "ID");
        Book b = new Book("Titulo", "Autor", "ID");
        Loan l = new Loan(b, u, LocalDate.now(), "ACTIVE", null);
        s.addLoan(l);
    }
}
