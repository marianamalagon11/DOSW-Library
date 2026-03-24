package edu.eci.dows.tdd.core.service;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.model.Loan;
import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.
        persistence.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan addLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    public List<Loan> getLoansByUser(User user) {
        return loanRepository.findAll()
                .stream()
                .filter(loan -> loan.getUser().equals(user))
                .collect(Collectors.toList());
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public List<Loan> getLoansByBook(Book book) {
        return loanRepository.findAll()
                .stream()
                .filter(loan -> loan.getBook().equals(book))
                .collect(Collectors.toList());
    }

    public Optional<Loan> getLoanById(String id) {
        return loanRepository.findById(id);
    }

    public void deleteLoan(String id) {
        loanRepository.deleteById(id);
    }

    public void updateLoan(String id, Loan updatedLoan) {
        if (loanRepository.existsById(id)) {
            updatedLoan.setId(id);
            loanRepository.save(updatedLoan);
        }
    }
}