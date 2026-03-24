package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.model.Loan;
import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.core.service.LoanService;
import edu.eci.dows.tdd.persistence.entity.BookEntity;
import edu.eci.dows.tdd.persistence.entity.LoanEntity;
import edu.eci.dows.tdd.persistence.entity.UserEntity;
import edu.eci.dows.tdd.persistence.repository.BookRepository;
import edu.eci.dows.tdd.persistence.repository.LoanRepository;
import edu.eci.dows.tdd.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoanServiceTest {
    @Test
    public void testAddLoanReturnsSavedLoan() {
        LoanRepository loanRepository = mock(LoanRepository.class);
        BookRepository bookRepository = mock(BookRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        LoanService service = new LoanService(loanRepository, bookRepository, userRepository);

        Book modelBook = new Book("CA1", "Cien anios", "Gabo", 4, 3);
        User modelUser = new User("U005", "Luis");
        Loan input = new Loan("L1", modelBook, modelUser, LocalDate.now(), "ACTIVE", null);

        BookEntity bookEntity = new BookEntity();
        bookEntity.setId("CA1");
        bookEntity.setTotalStock(4);
        bookEntity.setAvailableStock(3);
        UserEntity userEntity = new UserEntity();
        userEntity.setId("U005");

        LoanEntity savedEntity = new LoanEntity();
        savedEntity.setId("L1");
        savedEntity.setBook(bookEntity);
        savedEntity.setUser(userEntity);
        savedEntity.setLoanDate(input.getLoanDate());
        savedEntity.setStatus("ACTIVE");
        savedEntity.setReturnDate(null);

        when(bookRepository.findById("CA1")).thenReturn(Optional.of(bookEntity));
        when(userRepository.findById("U005")).thenReturn(Optional.of(userEntity));
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(savedEntity);

        Loan result = service.addLoan(input);
        assertEquals("L1", result.getId());
        assertEquals("CA1", result.getBook().getId());
        assertEquals("U005", result.getUser().getId());
    }

    @Test
    public void testGetAllLoans() {
        LoanRepository loanRepository = mock(LoanRepository.class);
        LoanService service = new LoanService(loanRepository, mock(BookRepository.class), mock(UserRepository.class));

        BookEntity book = new BookEntity();
        book.setId("R1");
        UserEntity user = new UserEntity();
        user.setId("U1");

        LoanEntity l1 = new LoanEntity();
        l1.setId("L2");
        l1.setBook(book);
        l1.setUser(user);
        l1.setLoanDate(LocalDate.now());

        LoanEntity l2 = new LoanEntity();
        l2.setId("L3");
        l2.setBook(book);
        l2.setUser(user);
        l2.setLoanDate(LocalDate.now());

        when(loanRepository.findAll()).thenReturn(List.of(l1, l2));

        assertEquals(2, service.getAllLoans().size());
    }

    @Test
    public void testGetLoanByIdWhenFound() {
        LoanRepository loanRepository = mock(LoanRepository.class);
        LoanService service = new LoanService(loanRepository, mock(BookRepository.class), mock(UserRepository.class));

        BookEntity book = new BookEntity();
        book.setId("R1");
        UserEntity user = new UserEntity();
        user.setId("U1");

        LoanEntity entity = new LoanEntity();
        entity.setId("L4");
        entity.setBook(book);
        entity.setUser(user);
        entity.setLoanDate(LocalDate.now());

        when(loanRepository.findById("L4")).thenReturn(Optional.of(entity));

        Optional<Loan> result = service.getLoanById("L4");
        assertTrue(result.isPresent());
        assertEquals("L4", result.get().getId());
    }

    @Test
    public void testUpdateLoanWhenExistsSavesEntity() {
        LoanRepository loanRepository = mock(LoanRepository.class);
        BookRepository bookRepository = mock(BookRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        LoanService service = new LoanService(loanRepository, bookRepository, userRepository);

        LoanEntity existing = new LoanEntity();
        existing.setId("L6");
        when(loanRepository.findById("L6")).thenReturn(Optional.of(existing));

        BookEntity bookEntity = new BookEntity();
        bookEntity.setId("B1");
        UserEntity userEntity = new UserEntity();
        userEntity.setId("U1");
        when(bookRepository.findById("B1")).thenReturn(Optional.of(bookEntity));
        when(userRepository.findById("U1")).thenReturn(Optional.of(userEntity));

        Loan updated = new Loan("other", new Book("B1", "1984", "Orwell", 10, 9), new User("U1", "Ana"), LocalDate.now(), "ACTIVE", null);
        service.updateLoan("L6", updated);

        verify(loanRepository).save(any(LoanEntity.class));
    }

    @Test
    public void testDeleteLoanDelegatesToRepository() {
        LoanRepository loanRepository = mock(LoanRepository.class);
        LoanService service = new LoanService(loanRepository, mock(BookRepository.class), mock(UserRepository.class));

        service.deleteLoan("L8");

        verify(loanRepository).deleteById("L8");
    }
}