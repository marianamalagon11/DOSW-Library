package edu.eci.dows.DOSW_Library;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.dows.tdd.controller.BookController;
import edu.eci.dows.tdd.controller.dto.BookDTO;
import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

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
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper().findAndRegisterModules();
    }

    @Test
    public void testAddBookReturnsCreated() throws Exception {
        BookDTO request = new BookDTO("B1", "Clean Code", "Robert C. Martin");

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("B1"))
                .andExpect(jsonPath("$.title").value("Clean Code"))
                .andExpect(jsonPath("$.author").value("Robert C. Martin"));
    }

    @Test
    public void testGetAllBooksReturnsOkWithList() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Set.of(
                new Book("Clean Code", "Robert C. Martin", "B1"),
                new Book("DDD", "Eric Evans", "B2")
        ));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[1].id").exists());
    }

    @Test
    public void testGetBookByIdReturnsOkWhenExists() throws Exception {
        when(bookService.getBookById("B1")).thenReturn(new Book("Clean Code", "Robert C. Martin", "B1"));

        mockMvc.perform(get("/books/B1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("B1"))
                .andExpect(jsonPath("$.title").value("Clean Code"));
    }

    @Test
    public void testGetBookByIdReturnsNotFoundWhenMissing() throws Exception {
        when(bookService.getBookById("B404")).thenReturn(null);

        mockMvc.perform(get("/books/B404"))
                .andExpect(status().isNotFound());
    }
}
