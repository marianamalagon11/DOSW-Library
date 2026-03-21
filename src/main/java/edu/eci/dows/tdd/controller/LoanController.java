package edu.eci.dows.tdd.controller;

import lombok.RequiredArgsConstructor;
import edu.eci.dows.tdd.controller.dto.LoanDTO;
import edu.eci.dows.tdd.controller.mapper.LoanMapper;
import edu.eci.dows.tdd.core.service.LoanService;
import edu.eci.dows.tdd.core.service.BookService;
import edu.eci.dows.tdd.core.service.UserService;
import edu.eci.dows.tdd.core.model.Loan;
import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.core.exception.BookNotAvailableException;
import edu.eci.dows.tdd.core.exception.UserNotFoundException;
import edu.eci.dows.tdd.core.exception.LoanLimitExceededException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;
    private final BookService bookService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<LoanDTO> addLoan(@RequestBody LoanDTO request) {
        Book book = bookService.getBookById(request.getBookId());
        if (book == null) {
            throw new BookNotAvailableException("No existe el libro con id: " + request.getBookId());
        }
        User user = userService.getUserById(request.getUserId());
        if (user == null) {
            throw new UserNotFoundException("No existe el usuario con id: " + request.getUserId());
        }
        Loan loan = LoanMapper.toModel(request, book, user);
        loanService.addLoan(loan);
        return new ResponseEntity<>(LoanMapper.toDTO(loan), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<java.util.List<LoanDTO>> getAllLoans() {
        return ResponseEntity.ok(
                loanService.getAllLoans().stream()
                        .map(LoanMapper::toDTO)
                        .toList()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable String id) {
        Loan loan = loanService.getLoanById(id);
        if (loan != null) {
            loanService.deleteLoan(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new LoanLimitExceededException("No existe el préstamo con id: " + id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanDTO> updateLoan(@PathVariable String id, @RequestBody LoanDTO request) {
        Loan existingLoan = loanService.getLoanById(id);
        if (existingLoan == null) {
            throw new LoanLimitExceededException("No existe el préstamo con id: " + id);
        }
        Book book = bookService.getBookById(request.getBookId());
        if (book == null) {
            throw new BookNotAvailableException("No existe el libro con id: " + request.getBookId());
        }
        User user = userService.getUserById(request.getUserId());
        if (user == null) {
            throw new UserNotFoundException("No existe el usuario con id: " + request.getUserId());
        }
        Loan updatedLoan = LoanMapper.toModel(request, book, user);
        updatedLoan.setId(id);
        loanService.updateLoan(id, updatedLoan);
        return ResponseEntity.ok(LoanMapper.toDTO(updatedLoan));
    }
}