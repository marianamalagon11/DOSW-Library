package edu.eci.dows.tdd.controller;

import edu.eci.dows.tdd.controller.dto.BookDTO;
import edu.eci.dows.tdd.controller.mapper.BookMapper;
import edu.eci.dows.tdd.core.service.BookService;
import edu.eci.dows.tdd.core.exception.BookNotAvailableException;
import edu.eci.dows.tdd.core.model.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO request) {
        Book book = BookMapper.toModel(request);
        Book saved = bookService.addBook(book);
        return new ResponseEntity<>(BookMapper.toDTO(saved), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(
                bookService.getAllBooks().stream()
                        .map(BookMapper::toDTO)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable String id) {
        return bookService.getBookById(id)
                .map(book -> ResponseEntity.ok(BookMapper.toDTO(book)))
                .orElseThrow(() -> new BookNotAvailableException("No existe el libro con id: " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        if (bookService.getBookById(id).isPresent()) {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new BookNotAvailableException("No existe el libro con id: " + id);
        }
    }
}