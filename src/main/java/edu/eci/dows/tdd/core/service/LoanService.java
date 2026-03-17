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
}