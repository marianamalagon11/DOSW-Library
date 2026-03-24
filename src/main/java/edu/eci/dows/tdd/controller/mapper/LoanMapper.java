package edu.eci.dows.tdd.controller.mapper;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.model.Loan;
import edu.eci.dows.tdd.controller.dto.LoanDTO;
import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.persistence.entity.LoanEntity;
import edu.eci.dows.tdd.persistence.entity.BookEntity;
import edu.eci.dows.tdd.persistence.entity.UserEntity;

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
    public static LoanEntity toEntity(Loan loan, BookEntity book, UserEntity user) {
        LoanEntity entity = new LoanEntity();
        entity.setId(loan.getId());
        entity.setBook(book);
        entity.setUser(user);
        entity.setLoanDate(loan.getLoanDate());
        entity.setStatus(loan.getStatus());
        entity.setReturnDate(loan.getReturnDate());
        return entity;
    }
    public static Loan toModel(LoanEntity entity) {
        return new Loan(
                entity.getId(),
                BookMapper.toModel(entity.getBook()),
                UserMapper.toModel(entity.getUser()),
                entity.getLoanDate(),
                entity.getStatus(),
                entity.getReturnDate()
        );
    }
}