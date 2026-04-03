package edu.eci.dows.tdd.controller;

import edu.eci.dows.tdd.controller.dto.LoanDTO;
import edu.eci.dows.tdd.controller.mapper.LoanMapper;
import edu.eci.dows.tdd.core.exception.BookNotAvailableException;
import edu.eci.dows.tdd.core.exception.LoanLimitExceededException;
import edu.eci.dows.tdd.core.exception.UserNotFoundException;
import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.model.Loan;
import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.core.service.BookService;
import edu.eci.dows.tdd.core.service.LoanService;
import edu.eci.dows.tdd.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;
    private final BookService bookService;
    private final UserService userService;

    @PreAuthorize("hasAnyRole('USER','LIBRARIAN')")
    @PostMapping
    public ResponseEntity<LoanDTO> addLoan(@RequestBody LoanDTO request, Authentication authentication) {
        try {
            Book book = bookService.getBookById(request.getBookId())
                    .orElseThrow(() -> new BookNotAvailableException("No existe el libro con id: " + request.getBookId()));
            User user = userService.getUserById(request.getUserId())
                    .orElseThrow(() -> new UserNotFoundException("No existe el usuario con id: " + request.getUserId()));

            if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
                String username = authentication.getName();
                String currentUserId = userService.findByUsername(username).orElseThrow().getId();
                if (!currentUserId.equals(request.getUserId())) {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            }

            Loan loanToCreate = LoanMapper.toModel(request, book, user);
            Loan loanCreated = loanService.addLoan(loanToCreate);
            return new ResponseEntity<>(LoanMapper.toDTO(loanCreated), HttpStatus.CREATED);
        } catch (BookNotAvailableException | UserNotFoundException | LoanLimitExceededException ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        List<LoanDTO> loans = loanService.getAllLoans().stream()
                .map(LoanMapper::toDTO)
                .toList();
        return ResponseEntity.ok(loans);
    }

    @PreAuthorize("hasAnyRole('USER','LIBRARIAN')")
    @GetMapping("/me")
    public ResponseEntity<List<LoanDTO>> getMyLoans(Authentication authentication) {
        String username = authentication.getName();
        String userId = userService.findByUsername(username).orElseThrow().getId();
        List<LoanDTO> loans = loanService.getLoansByUserId(userId).stream()
                .map(LoanMapper::toDTO)
                .toList();
        return ResponseEntity.ok(loans);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> getLoanById(@PathVariable String id) {
        return loanService.getLoanById(id)
                .map(LoanMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable String id) {
        if (loanService.getLoanById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PutMapping("/{id}")
    public ResponseEntity<LoanDTO> updateLoan(@PathVariable String id, @RequestBody LoanDTO request) {
        if (loanService.getLoanById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            Book book = bookService.getBookById(request.getBookId())
                    .orElseThrow(() -> new BookNotAvailableException("No existe el libro con id: " + request.getBookId()));
            User user = userService.getUserById(request.getUserId())
                    .orElseThrow(() -> new UserNotFoundException("No existe el usuario con id: " + request.getUserId()));
            Loan updatedLoan = LoanMapper.toModel(request, book, user);
            loanService.updateLoan(id, updatedLoan);
            return loanService.getLoanById(id)
                    .map(LoanMapper::toDTO)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (BookNotAvailableException | UserNotFoundException | LoanLimitExceededException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('USER','LIBRARIAN')")
    @PutMapping("/{id}/return")
    public ResponseEntity<Void> returnLoan(@PathVariable String id, Authentication authentication) {
        var loanOpt = loanService.getLoanById(id);
        if (loanOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String username = authentication.getName();
        String currentUserId = userService.findByUsername(username).orElseThrow().getId();

        boolean isLibrarian = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_LIBRARIAN"));
        if (!isLibrarian && !loanService.loanBelongsToUser(id, currentUserId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Loan existing = loanOpt.get();
        LoanDTO dto = LoanMapper.toDTO(existing);
        LoanDTO returned = new LoanDTO(
                dto.getId(),
                dto.getBookId(),
                dto.getUserId(),
                dto.getLoanDate(),
                "DEVUELTO",
                LocalDate.now(),
                dto.getRecord()
        );

        Book book = bookService.getBookById(returned.getBookId()).orElseThrow();
        User user = userService.getUserById(returned.getUserId()).orElseThrow();
        loanService.updateLoan(id, LoanMapper.toModel(returned, book, user));
        return ResponseEntity.noContent().build();
    }
}