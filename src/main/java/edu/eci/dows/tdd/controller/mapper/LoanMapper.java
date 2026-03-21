package edu.eci.dows.tdd.controller.mapper;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.model.Loan;
import edu.eci.dows.tdd.controller.dto.LoanDTO;
import edu.eci.dows.tdd.core.model.User;

public class LoanMapper {
    public static LoanDTO toDTO(Loan loan) {
        return new LoanDTO(
                loan.getId(),
                loan.getBook().getId(),
                loan.getUser().getId(),
                loan.getLoanDate(),
                loan.getStatus(),
                loan.getReturnDate()
        );
    }

    public static Loan toModel(LoanDTO dto, Book book, User user) {
        return new Loan(
                dto.getId(),
                book,
                user,
                dto.getLoanDate(),
                dto.getStatus(),
                dto.getReturnDate()
        );
    }
}