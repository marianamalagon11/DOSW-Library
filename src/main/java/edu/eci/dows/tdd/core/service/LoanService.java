package edu.eci.dows.tdd.core.service;

import edu.eci.dows.tdd.core.exception.BookNotAvailableException;
import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.model.Loan;
import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.persistence.port.BookRepositoryPort;
import edu.eci.dows.tdd.persistence.port.LoanRepositoryPort;
import edu.eci.dows.tdd.persistence.port.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final LoanRepositoryPort loanRepository;
    private final BookRepositoryPort bookRepository;
    private final UserRepositoryPort userRepository;

    public LoanService(LoanRepositoryPort loanRepository,
                       BookRepositoryPort bookRepository,
                       UserRepositoryPort userRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Loan addLoan(Loan loan) {
        Book book = bookRepository.findById(loan.getBook().getId()).orElseThrow();
        if (book.getAvailableStock() <= 0) {
            throw new BookNotAvailableException("No hay stock disponible para el libro con id: " + book.getId());
        }


        book.setAvailableStock(book.getAvailableStock() - 1);
        if (book.getAvailability() != null && book.getAvailability().getAvailableCopies() != null) {
            book.getAvailability().setAvailableCopies(book.getAvailableStock());
            if (book.getAvailability().getTotalCopies() != null) {
                book.getAvailability().setBorrowedCopies(Math.max(book.getAvailability().getTotalCopies() - book.getAvailability().getAvailableCopies(), 0));
            }
        }
        bookRepository.save(book);

        User user = userRepository.findById(loan.getUser().getId()).orElseThrow();


        if (loan.getRecord() == null || loan.getRecord().isEmpty()) {
            loan.setRecord(List.of(new Loan.RecordEntry(loan.getStatus(), Instant.now().toString())));
        }

        loan.setBook(book);
        loan.setUser(user);

        return loanRepository.save(loan);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public List<Loan> getLoansByUserId(String userId) {
        return loanRepository.findAllByUserId(userId);
    }

    public boolean loanBelongsToUser(String loanId, String userId) {
        return loanRepository.existsByIdAndUserId(loanId, userId);
    }

    public Optional<Loan> getLoanById(String id) {
        return loanRepository.findById(id);
    }

    public void deleteLoan(String id) {
        loanRepository.deleteById(id);
    }

    public void updateLoan(String id, Loan updatedLoan) {
        Optional<Loan> existingOpt = loanRepository.findById(id);
        if (existingOpt.isEmpty()) return;

        Loan existing = existingOpt.get();
        String prevStatus = existing.getStatus();
        String newStatus = updatedLoan.getStatus();

        Book book = bookRepository.findById(updatedLoan.getBook().getId()).orElseThrow();
        User user = userRepository.findById(updatedLoan.getUser().getId()).orElseThrow();


        if (!"DEVUELTO".equalsIgnoreCase(prevStatus)
                && ("DEVUELTO".equalsIgnoreCase(newStatus) || "RETURNED".equalsIgnoreCase(newStatus))) {
            if (book.getAvailableStock() < book.getTotalStock()) {
                book.setAvailableStock(book.getAvailableStock() + 1);
                if (book.getAvailability() != null && book.getAvailability().getAvailableCopies() != null) {
                    book.getAvailability().setAvailableCopies(book.getAvailableStock());
                    if (book.getAvailability().getTotalCopies() != null) {
                        book.getAvailability().setBorrowedCopies(Math.max(book.getAvailability().getTotalCopies() - book.getAvailability().getAvailableCopies(), 0));
                    }
                }
                bookRepository.save(book);
            }
        }


        existing.setBook(book);
        existing.setUser(user);
        existing.setLoanDate(updatedLoan.getLoanDate());
        existing.setStatus(updatedLoan.getStatus());
        existing.setReturnDate(updatedLoan.getReturnDate());

        if (updatedLoan.getRecord() != null && !updatedLoan.getRecord().isEmpty()) {
            existing.setRecord(updatedLoan.getRecord());
        } else {
            List<Loan.RecordEntry> r = existing.getRecord() == null ? new java.util.ArrayList<>() : new java.util.ArrayList<>(existing.getRecord());
            r.add(new Loan.RecordEntry(existing.getStatus(), Instant.now().toString()));
            existing.setRecord(r);
        }

        loanRepository.save(existing);
    }
}