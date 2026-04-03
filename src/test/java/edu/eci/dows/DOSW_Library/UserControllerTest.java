package edu.eci.dows.DOSW_Library;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.dows.tdd.controller.UserController;
import edu.eci.dows.tdd.controller.dto.CreateUserRequestDTO;
import edu.eci.dows.tdd.core.exception.GlobalExceptionHandler;
import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.core.service.UserService;
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

public class UserControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private UserService userService;

    private User buildUser(String id, String name, String username, String password, String role) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }

    @BeforeEach
    public void setUp() {
        userService = mock(UserService.class);
        UserController controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper().findAndRegisterModules();
    }

    @Test
    public void testAddUserReturnsCreated() throws Exception {
        CreateUserRequestDTO request = new CreateUserRequestDTO("U1", "Maria", "maria", "1234", "USER", null, null, null);
        when(userService.createUser(org.mockito.ArgumentMatchers.any(CreateUserRequestDTO.class)))
                .thenReturn(buildUser("U1", "Maria", "maria", "encoded", "USER"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("U1"))
                .andExpect(jsonPath("$.name").value("Maria"))
                .andExpect(jsonPath("$.username").value("maria"))
                .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    public void testGetAllUsersReturnsOkWithList() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(
                buildUser("U1", "Maria", "maria", "secret", "USER"),
                buildUser("U2", "Ana", "ana", "secret", "LIBRARIAN")
        ));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("U1"))
                .andExpect(jsonPath("$[1].id").value("U2"));
    }

    @Test
    public void testGetUserByIdReturnsOkWhenExists() throws Exception {
        when(userService.getUserById("U1")).thenReturn(Optional.of(buildUser("U1", "Maria", "maria", "secret", "USER")));

        mockMvc.perform(get("/users/U1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("U1"))
                .andExpect(jsonPath("$.name").value("Maria"));
    }

    @Test
    public void testGetUserByIdMissingReturnsServerError() throws Exception {
        when(userService.getUserById("U404")).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/U404"))
                .andExpect(status().isNotFound());
    }
}
