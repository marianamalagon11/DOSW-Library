package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.model.Loan;
import edu.eci.dows.tdd.core.service.LoanService;
import edu.eci.dows.tdd.persistence.port.BookRepositoryPort;
import edu.eci.dows.tdd.persistence.port.LoanRepositoryPort;
import edu.eci.dows.tdd.persistence.port.UserRepositoryPort;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoanServiceReto6Test {

    private static Loan loanWithId(String id) {
        return new Loan(
                id,
                null,
                null,
                LocalDate.now(),
                "ACTIVO",
                null,
                List.of()
        );
    }

    @Test
    void dadoQueTengo1ReservaRegistrada_cuandoConsultoServicio_entoncesExitosoValidandoId() {

        LoanRepositoryPort loanRepo = mock(LoanRepositoryPort.class);
        BookRepositoryPort bookRepo = mock(BookRepositoryPort.class);
        UserRepositoryPort userRepo = mock(UserRepositoryPort.class);

        LoanService service = new LoanService(loanRepo, bookRepo, userRepo);

        when(loanRepo.findAll()).thenReturn(List.of(loanWithId("loan-1")));


        List<Loan> result = service.getAllLoans();


        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("loan-1", result.get(0).getId()); // Lombok getter
    }

    @Test
    void dadoQueNoHayReservasRegistradas_cuandoConsultoServicio_entoncesNoRetornaResultado() {

        LoanRepositoryPort loanRepo = mock(LoanRepositoryPort.class);
        BookRepositoryPort bookRepo = mock(BookRepositoryPort.class);
        UserRepositoryPort userRepo = mock(UserRepositoryPort.class);

        LoanService service = new LoanService(loanRepo, bookRepo, userRepo);

        when(loanRepo.findAll()).thenReturn(List.of());


        List<Loan> result = service.getAllLoans();


        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void dadoQueNoHayReservasRegistradas_cuandoEliminoServicio_entoncesEliminacionExitosa() {

        LoanRepositoryPort loanRepo = mock(LoanRepositoryPort.class);
        BookRepositoryPort bookRepo = mock(BookRepositoryPort.class);
        UserRepositoryPort userRepo = mock(UserRepositoryPort.class);

        LoanService service = new LoanService(loanRepo, bookRepo, userRepo);

        doNothing().when(loanRepo).deleteById("loan-x");


        assertDoesNotThrow(() -> service.deleteLoan("loan-x"));
        verify(loanRepo, times(1)).deleteById("loan-x");
    }

    @Test
    void dadoQueTengo1ReservaRegistrada_cuandoEliminoServicio_entoncesEliminacionExitosa() {

        LoanRepositoryPort loanRepo = mock(LoanRepositoryPort.class);
        BookRepositoryPort bookRepo = mock(BookRepositoryPort.class);
        UserRepositoryPort userRepo = mock(UserRepositoryPort.class);

        LoanService service = new LoanService(loanRepo, bookRepo, userRepo);

        doNothing().when(loanRepo).deleteById("loan-1");


        service.deleteLoan("loan-1");


        verify(loanRepo, times(1)).deleteById("loan-1");
    }

    @Test
    void dadoQueTengo1ReservaRegistrada_cuandoEliminoYConsultoServicio_entoncesNoRetornaResultado() {

        LoanRepositoryPort loanRepo = mock(LoanRepositoryPort.class);
        BookRepositoryPort bookRepo = mock(BookRepositoryPort.class);
        UserRepositoryPort userRepo = mock(UserRepositoryPort.class);

        LoanService service = new LoanService(loanRepo, bookRepo, userRepo);

        when(loanRepo.findAll()).thenReturn(List.of(loanWithId("loan-1")));
        assertEquals(1, service.getAllLoans().size());

        doNothing().when(loanRepo).deleteById("loan-1");
        service.deleteLoan("loan-1");
        verify(loanRepo, times(1)).deleteById("loan-1");

        when(loanRepo.findAll()).thenReturn(List.of());

        List<Loan> after = service.getAllLoans();
        assertNotNull(after);
        assertTrue(after.isEmpty());
    }
}