package edu.eci.dows.tdd.controller;

import lombok.RequiredArgsConstructor;
import edu.eci.dows.tdd.controller.dto.LoanDTO;
import edu.eci.dows.tdd.controller.mapper.LoanMapper;
import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.model.Loan;
import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.core.service.BookService;
import edu.eci.dows.tdd.core.service.LoanService;
import edu.eci.dows.tdd.core.service.UserService;
import edu.eci.dows.tdd.core.exception.LoanLimitExceededException;
import edu.eci.dows.tdd.core.exception.BookNotAvailableException;
import edu.eci.dows.tdd.core.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;
    private final BookService bookService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<LoanDTO> addLoan(@RequestBody LoanDTO request) {
        try {
            Book book = bookService.getBookById(request.getBookId()).orElseThrow();
            User user = userService.getUserById(request.getUserId()).orElseThrow();
            Loan loanToCreate = LoanMapper.toModel(request, book, user);
            Loan loanCreated = loanService.addLoan(loanToCreate);
            return new ResponseEntity<>(LoanMapper.toDTO(loanCreated), HttpStatus.CREATED);
        } catch (BookNotAvailableException | UserNotFoundException | LoanLimitExceededException ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        List<LoanDTO> loans = loanService.getAllLoans().stream()
                .map(LoanMapper::toDTO)
                .toList();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> getLoanById(@PathVariable String id) {
        return loanService.getLoanById(id)
                .map(LoanMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable String id) {
        if (loanService.getLoanById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanDTO> updateLoan(@PathVariable String id, @RequestBody LoanDTO request) {
        if (loanService.getLoanById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        try {
            Book book = bookService.getBookById(request.getBookId()).orElseThrow();
            User user = userService.getUserById(request.getUserId()).orElseThrow();
            Loan updatedLoan = LoanMapper.toModel(request, book, user);
            loanService.updateLoan(id, updatedLoan);
            return loanService.getLoanById(id)
                    .map(LoanMapper::toDTO)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (BookNotAvailableException | UserNotFoundException | LoanLimitExceededException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}