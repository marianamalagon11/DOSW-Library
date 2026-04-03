package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.model.Loan;
import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.core.service.LoanService;
import edu.eci.dows.tdd.persistence.port.BookRepositoryPort;
import edu.eci.dows.tdd.persistence.port.LoanRepositoryPort;
import edu.eci.dows.tdd.persistence.port.UserRepositoryPort;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoanServiceTest {

    private Book buildBook(String id, String title, String author, int totalStock, int availableStock) {
        return new Book(id, title, author, totalStock, availableStock, null, null, null, null, null, null, null);
    }

    private User buildUser(String id, String name, String username, String password, String role) {
        return new User(id, name, username, password, role, null, null, null);
    }

    @Test
    public void testAddLoanReturnsSavedLoan() {
        LoanRepositoryPort loanRepository = mock(LoanRepositoryPort.class);
        BookRepositoryPort bookRepository = mock(BookRepositoryPort.class);
        UserRepositoryPort userRepository = mock(UserRepositoryPort.class);
        LoanService service = new LoanService(loanRepository, bookRepository, userRepository);

        Book modelBook = buildBook("CA1", "Cien anios", "Gabo", 4, 3);
        User modelUser = buildUser("U005", "Luis", "luis", "secret", "USER");
        Loan input = new Loan("L1", modelBook, modelUser, LocalDate.now(), "ACTIVE", null, null);

        when(bookRepository.findById("CA1")).thenReturn(Optional.of(modelBook));
        when(userRepository.findById("U005")).thenReturn(Optional.of(modelUser));
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Loan result = service.addLoan(input);
        assertEquals("L1", result.getId());
        assertEquals("CA1", result.getBook().getId());
        assertEquals("U005", result.getUser().getId());
    }

    @Test
    public void testGetAllLoans() {
        LoanRepositoryPort loanRepository = mock(LoanRepositoryPort.class);
        LoanService service = new LoanService(loanRepository, mock(BookRepositoryPort.class), mock(UserRepositoryPort.class));

        Book book = buildBook("R1", "Title", "Author", 2, 1);
        User user = buildUser("U1", "Ana", "ana", "secret", "USER");

        Loan l1 = new Loan("L2", book, user, LocalDate.now(), "ACTIVE", null, null);
        Loan l2 = new Loan("L3", book, user, LocalDate.now(), "RETURNED", null, null);

        when(loanRepository.findAll()).thenReturn(List.of(l1, l2));

        assertEquals(2, service.getAllLoans().size());
    }

    @Test
    public void testGetLoanByIdWhenFound() {
        LoanRepositoryPort loanRepository = mock(LoanRepositoryPort.class);
        LoanService service = new LoanService(loanRepository, mock(BookRepositoryPort.class), mock(UserRepositoryPort.class));

        Book book = buildBook("R1", "Title", "Author", 2, 1);
        User user = buildUser("U1", "Ana", "ana", "secret", "USER");
        Loan entity = new Loan("L4", book, user, LocalDate.now(), "ACTIVE", null, null);

        when(loanRepository.findById("L4")).thenReturn(Optional.of(entity));

        Optional<Loan> result = service.getLoanById("L4");
        assertTrue(result.isPresent());
        assertEquals("L4", result.get().getId());
    }

    @Test
    public void testUpdateLoanWhenExistsSavesEntity() {
        LoanRepositoryPort loanRepository = mock(LoanRepositoryPort.class);
        BookRepositoryPort bookRepository = mock(BookRepositoryPort.class);
        UserRepositoryPort userRepository = mock(UserRepositoryPort.class);
        LoanService service = new LoanService(loanRepository, bookRepository, userRepository);

        Loan existing = new Loan("L6", buildBook("B0", "Old", "Old", 1, 1), buildUser("U0", "Old", "old", "secret", "USER"), LocalDate.now(), "ACTIVE", null, null);
        when(loanRepository.findById("L6")).thenReturn(Optional.of(existing));

        Book book = buildBook("B1", "1984", "Orwell", 10, 9);
        User user = buildUser("U1", "Ana", "ana", "secret", "USER");
        when(bookRepository.findById("B1")).thenReturn(Optional.of(book));
        when(userRepository.findById("U1")).thenReturn(Optional.of(user));

        Loan updated = new Loan("other", book, user, LocalDate.now(), "ACTIVE", null, null);
        service.updateLoan("L6", updated);

        verify(loanRepository).save(any(Loan.class));
    }

    @Test
    public void testDeleteLoanDelegatesToRepository() {
        LoanRepositoryPort loanRepository = mock(LoanRepositoryPort.class);
        LoanService service = new LoanService(loanRepository, mock(BookRepositoryPort.class), mock(UserRepositoryPort.class));

        service.deleteLoan("L8");

        verify(loanRepository).deleteById("L8");
    }
}