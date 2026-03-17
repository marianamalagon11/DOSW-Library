package edu.eci.dows.tdd.controller;

import edu.eci.dows.tdd.controller.dto.BookDTO;
import edu.eci.dows.tdd.controller.mapper.BookMapper;
import edu.eci.dows.tdd.core.service.BookService;
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
        bookService.addBook(book, 1);
        return new ResponseEntity<>(BookMapper.toDTO(book), HttpStatus.CREATED);
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
        Book book = bookService.getBookById(id);
        if (book != null) {
            return ResponseEntity.ok(BookMapper.toDTO(book));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}