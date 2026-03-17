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
        User user = userService.getUserById(request.getUserId());
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
}