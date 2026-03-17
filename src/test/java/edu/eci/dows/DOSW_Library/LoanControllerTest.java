package edu.eci.dows.DOSW_Library;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.dows.tdd.controller.LoanController;
import edu.eci.dows.tdd.controller.dto.LoanDTO;
import edu.eci.dows.tdd.core.model.Book;
import edu.eci.dows.tdd.core.model.Loan;
import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.core.service.BookService;
import edu.eci.dows.tdd.core.service.LoanService;
import edu.eci.dows.tdd.core.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoanControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private LoanService loanService;
    private BookService bookService;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        loanService = mock(LoanService.class);
        bookService = mock(BookService.class);
        userService = mock(UserService.class);
        LoanController controller = new LoanController(loanService, bookService, userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper().findAndRegisterModules();
    }

    @Test
    public void testAddLoanReturnsCreated() throws Exception {
        Book book = new Book("1984", "Orwell", "B1");
        User user = new User("Maria", "U1");
        when(bookService.getBookById("B1")).thenReturn(book);
        when(userService.getUserById("U1")).thenReturn(user);

        LoanDTO request = new LoanDTO("B1", "U1", LocalDate.of(2026, 3, 1), "ACTIVE", null);

        mockMvc.perform(post("/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookId").value("B1"))
                .andExpect(jsonPath("$.userId").value("U1"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    public void testGetAllLoansReturnsOkWithList() throws Exception {
        Loan loan1 = new Loan(
                new Book("1984", "Orwell", "B1"),
                new User("Maria", "U1"),
                LocalDate.of(2026, 3, 1),
                "ACTIVE",
                null
        );
        Loan loan2 = new Loan(
                new Book("DDD", "Evans", "B2"),
                new User("Ana", "U2"),
                LocalDate.of(2026, 3, 2),
                "RETURNED",
                LocalDate.of(2026, 3, 10)
        );
        when(loanService.getAllLoans()).thenReturn(List.of(loan1, loan2));

        mockMvc.perform(get("/loans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookId").value("B1"))
                .andExpect(jsonPath("$[1].bookId").value("B2"));
    }
}
