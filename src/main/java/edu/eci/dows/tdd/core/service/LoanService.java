package edu.eci.dows.tdd.core.service;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.model.Loan;
import edu.eci.dows.tdd.core.model.User;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Data
public class LoanService {
    private List<Loan> loans = new ArrayList<>();

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public List<Loan> getLoansByUser(User user) {
        List<Loan> result = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getUser().equals(user)) {
                result.add(loan);
            }
        }
        return result;
    }

    public List<Loan> getAllLoans() {
        return loans;
    }

    public List<Loan> getLoansByBook(Book book) {
        List<Loan> result = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getBook().equals(book)) {
                result.add(loan);
            }
        }
        return result;
    }

    public Loan getLoanById(String id) {
        return loans.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst().orElse(null);
    }

    public void deleteLoan(String id) {
        loans.removeIf(l -> l.getId().equals(id));
    }

    public void updateLoan(String id, Loan updatedLoan) {
        for (int i = 0; i < loans.size(); i++) {
            if (loans.get(i).getId().equals(id)) {
                loans.set(i, updatedLoan);
                return;
            }
        }
    }
}