package edu.eci.dows.tdd.controller;

import edu.eci.dows.tdd.controller.dto.BookDTO;
import edu.eci.dows.tdd.controller.mapper.BookMapper;
import edu.eci.dows.tdd.core.exception.BookNotAvailableException;
import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO request) {
        Book book = BookMapper.toModel(request);
        Book saved = bookService.addBook(book);
        return new ResponseEntity<>(BookMapper.toDTO(saved), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('USER','LIBRARIAN')")
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(
                bookService.getAllBooks().stream()
                        .map(BookMapper::toDTO)
                        .toList()
        );
    }

    @PreAuthorize("hasAnyRole('USER','LIBRARIAN')")
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable String id) {
        return bookService.getBookById(id)
                .map(book -> ResponseEntity.ok(BookMapper.toDTO(book)))
                .orElseThrow(() -> new BookNotAvailableException("No existe el libro con id: " + id));
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        if (bookService.getBookById(id).isPresent()) {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        }
        throw new BookNotAvailableException("No existe el libro con id: " + id);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable String id, @RequestBody BookDTO request) {
        Book updatedBook = BookMapper.toModel(request);
        return bookService.updateBook(id, updatedBook)
                .map(book -> ResponseEntity.ok(BookMapper.toDTO(book)))
                .orElseThrow(() -> new BookNotAvailableException("No existe el libro con id: " + id));
    }
}