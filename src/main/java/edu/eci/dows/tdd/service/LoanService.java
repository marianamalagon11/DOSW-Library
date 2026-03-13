package edu.eci.dows.tdd.service;

import edu.eci.dows.tdd.model.Loan;
import java.util.*;

public class LoanService {
    private List<Loan> loans = new ArrayList<>();

    public void addLoan(Loan loan) {
        loans.add(loan);
    }
}