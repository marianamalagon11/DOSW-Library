package edu.eci.dows.DOSW_Library;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.dows.tdd.controller.UserController;
import edu.eci.dows.tdd.controller.dto.UserDTO;
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

    @BeforeEach
    public void setUp() {
        userService = mock(UserService.class);
        UserController controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper().findAndRegisterModules();
    }

    @Test
    public void testAddUserReturnsCreated() throws Exception {
        UserDTO request = new UserDTO("U1", "Maria");
        when(userService.addUser(org.mockito.ArgumentMatchers.any(User.class)))
                .thenReturn(new User("Maria", "U1"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("U1"))
                .andExpect(jsonPath("$.name").value("Maria"));
    }

    @Test
    public void testGetAllUsersReturnsOkWithList() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(
                new User("Maria", "U1"),
                new User("Ana", "U2")
        ));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("U1"))
                .andExpect(jsonPath("$[1].id").value("U2"));
    }

    @Test
    public void testGetUserByIdReturnsOkWhenExists() throws Exception {
        when(userService.getUserById("U1")).thenReturn(Optional.of(new User("Maria", "U1")));

        mockMvc.perform(get("/users/U1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("U1"))
                .andExpect(jsonPath("$.name").value("Maria"));
    }

    @Test
    public void testGetUserByIdMissingReturnsServerError() throws Exception {
        when(userService.getUserById("U404")).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/U404"))
                .andExpect(status().is5xxServerError());
    }
}
