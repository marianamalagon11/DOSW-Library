package edu.eci.dows.tdd.controller.mapper;

import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.model.Loan;
import edu.eci.dows.tdd.controller.dto.LoanDTO;
import edu.eci.dows.tdd.core.model.User;

import java.util.List;

public class LoanMapper {
    public static LoanDTO toDTO(Loan loan) {
        List<LoanDTO.RecordDTO> record = null;
        if (loan.getRecord() != null) {
            record = loan.getRecord().stream()
                    .map(r -> new LoanDTO.RecordDTO(r.getStatus(), r.getDate()))
                    .toList();
        }

        return new LoanDTO(
                loan.getId(),
                loan.getBook().getId(),
                loan.getUser().getId(),
                loan.getLoanDate(),
                loan.getStatus(),
                loan.getReturnDate(),
                record
        );
    }

    public static Loan toModel(LoanDTO dto, Book book, User user) {
        List<Loan.RecordEntry> record = null;
        if (dto.getRecord() != null) {
            record = dto.getRecord().stream()
                    .map(r -> new Loan.RecordEntry(r.getStatus(), r.getDate()))
                    .toList();
        }

        return new Loan(
                dto.getId(),
                book,
                user,
                dto.getLoanDate(),
                dto.getStatus(),
                dto.getReturnDate(),
                record
        );
    }
}