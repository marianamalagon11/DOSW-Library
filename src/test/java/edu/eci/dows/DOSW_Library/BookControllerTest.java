package edu.eci.dows.DOSW_Library;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.dows.tdd.controller.BookController;
import edu.eci.dows.tdd.controller.dto.BookDTO;
import edu.eci.dows.tdd.core.exception.GlobalExceptionHandler;
import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        bookService = mock(BookService.class);
        BookController controller = new BookController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper().findAndRegisterModules();
    }

    @Test
    public void testAddBookReturnsCreated() throws Exception {
        BookDTO request = new BookDTO("B1", "Clean Code", "Robert C. Martin", 8, 8);
        when(bookService.addBook(org.mockito.ArgumentMatchers.any(Book.class)))
                .thenReturn(new Book("B1", "Clean Code", "Robert C. Martin", 8, 8));

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("B1"))
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"))
                .andExpect(jsonPath("$.totalStock").value(8));
    }

    @Test
    public void testGetAllBooksReturnsOkWithList() throws Exception {
        when(bookService.getAllBooks()).thenReturn(List.of(
                new Book("B1", "Clean Code", "Robert C. Martin", 6, 5),
                new Book("B2", "DDD", "Eric Evans", 4, 4)
        ));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("B1"))
                .andExpect(jsonPath("$[1].id").value("B2"));
    }

    @Test
    public void testGetBookByIdReturnsOkWhenExists() throws Exception {
        when(bookService.getBookById("B1")).thenReturn(Optional.of(new Book("B1", "Clean Code", "Robert C. Martin", 8, 7)));

        mockMvc.perform(get("/books/B1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("B1"))
                .andExpect(jsonPath("$.title").value("Clean Code"));
    }

    @Test
    public void testGetBookByIdMissingReturnsServerError() throws Exception {
        when(bookService.getBookById("B404")).thenReturn(Optional.empty());

        mockMvc.perform(get("/books/B404"))
                .andExpect(status().isBadRequest());
    }
}
