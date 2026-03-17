package edu.eci.dows.tdd.core.validator;

import edu.eci.dows.tdd.core.model.Loan;

public class LoanValidator {
    public static boolean isValid(Loan loan) {
        return loan != null && loan.getBook() != null && loan.getUser() != null && loan.getLoanDate() != null;
    }
}
