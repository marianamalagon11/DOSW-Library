package edu.eci.dows.tdd.core.service;

import edu.eci.dows.tdd.core.model.Loan;
import edu.eci.dows.tdd.persistence.repository.LoanRepository;
import edu.eci.dows.tdd.persistence.repository.BookRepository;
import edu.eci.dows.tdd.persistence.repository.UserRepository;
import edu.eci.dows.tdd.persistence.entity.LoanEntity;
import edu.eci.dows.tdd.persistence.entity.BookEntity;
import edu.eci.dows.tdd.persistence.entity.UserEntity;
import edu.eci.dows.tdd.controller.mapper.LoanMapper;
import edu.eci.dows.tdd.core.exception.BookNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository,
                       BookRepository bookRepository,
                       UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Loan addLoan(Loan loan) {
        BookEntity bookEntity = bookRepository.findById(loan.getBook().getId()).orElseThrow();
        if (bookEntity.getAvailableStock() <= 0) {
            throw new BookNotAvailableException("No hay stock disponible para el libro con id: " + bookEntity.getId());
        }
        bookEntity.setAvailableStock(bookEntity.getAvailableStock() - 1);
        bookRepository.save(bookEntity);

        UserEntity userEntity = userRepository.findById(loan.getUser().getId()).orElseThrow();
        LoanEntity entity = LoanMapper.toEntity(loan, bookEntity, userEntity);
        LoanEntity saved = loanRepository.save(entity);
        return LoanMapper.toModel(saved);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll().stream()
                .map(LoanMapper::toModel)
                .toList();
    }

    public Optional<Loan> getLoanById(String id) {
        return loanRepository.findById(id).map(LoanMapper::toModel);
    }

    public void deleteLoan(String id) {
        loanRepository.deleteById(id);
    }

    public void updateLoan(String id, Loan updatedLoan) {
        Optional<LoanEntity> entityOpt = loanRepository.findById(id);
        if (entityOpt.isPresent()) {
            LoanEntity existingLoan = entityOpt.get();
            String prevStatus = existingLoan.getStatus();
            String newStatus = updatedLoan.getStatus();

            BookEntity bookEntity = bookRepository.findById(updatedLoan.getBook().getId()).orElseThrow();
            UserEntity userEntity = userRepository.findById(updatedLoan.getUser().getId()).orElseThrow();

            if (
                    !"DEVUELTO".equalsIgnoreCase(prevStatus)
                            && ("DEVUELTO".equalsIgnoreCase(newStatus) || "RETURNED".equalsIgnoreCase(newStatus))
            ) {
                if (bookEntity.getAvailableStock() < bookEntity.getTotalStock()) {
                    bookEntity.setAvailableStock(bookEntity.getAvailableStock() + 1);
                    bookRepository.save(bookEntity);
                }
            }

            LoanEntity entity = LoanMapper.toEntity(updatedLoan, bookEntity, userEntity);
            entity.setId(id);
            loanRepository.save(entity);
        }
    }
}