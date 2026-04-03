package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.controller.dto.CreateUserRequestDTO;
import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.core.service.UserService;
import edu.eci.dows.tdd.persistence.port.UserRepositoryPort;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private User buildUser(String id, String name, String username, String password, String role) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }

    @Test
    public void testCreateUserReturnsSavedUser() {
        UserRepositoryPort repository = mock(UserRepositoryPort.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        UserService service = new UserService(repository, passwordEncoder);

        CreateUserRequestDTO request = new CreateUserRequestDTO("U001", "Mari", "mari", "1234", "USER", "mari@test.com", "PREMIUM", "2026-04-01T00:00:00Z");
        User saved = buildUser("U001", "Mari", "mari", "encoded", "USER");
        saved.setEmail("mari@test.com");
        saved.setMembershipType("PREMIUM");
        saved.setAddedAt("2026-04-01T00:00:00Z");
        when(repository.existsByUsername("mari")).thenReturn(false);
        when(passwordEncoder.encode("1234")).thenReturn("encoded");
        when(repository.save(any(User.class))).thenReturn(saved);

        User result = service.createUser(request);
        assertEquals("U001", result.getId());
        assertEquals("Mari", result.getName());
        assertEquals("mari", result.getUsername());
        assertEquals("USER", result.getRole());
        assertEquals("mari@test.com", result.getEmail());
    }

    @Test
    public void testGetAllUsers() {
        UserRepositoryPort repository = mock(UserRepositoryPort.class);
        UserService service = new UserService(repository, mock(PasswordEncoder.class));

        User u1 = buildUser("U002", "Shawn", "shawn", "secret", "USER");
        User u2 = buildUser("U003", "Joji", "joji", "secret", "LIBRARIAN");

        when(repository.findAll()).thenReturn(List.of(u1, u2));

        List<User> users = service.getAllUsers();
        assertEquals(2, users.size());
        assertEquals("U003", users.get(1).getId());
    }

    @Test
    public void testGetUserByIdWhenFound() {
        UserRepositoryPort repository = mock(UserRepositoryPort.class);
        UserService service = new UserService(repository, mock(PasswordEncoder.class));

        User entity = buildUser("U001", "Mari", "mari", "secret", "USER");
        when(repository.findById("U001")).thenReturn(Optional.of(entity));

        Optional<User> result = service.getUserById("U001");
        assertTrue(result.isPresent());
        assertEquals("Mari", result.get().getName());
    }

    @Test
    public void testGetUserByIdWhenNotFoundReturnsEmpty() {
        UserRepositoryPort repository = mock(UserRepositoryPort.class);
        UserService service = new UserService(repository, mock(PasswordEncoder.class));

        when(repository.findById("U404")).thenReturn(Optional.empty());

        assertTrue(service.getUserById("U404").isEmpty());
    }

    @Test
    public void testDeleteUserDelegatesToRepository() {
        UserRepositoryPort repository = mock(UserRepositoryPort.class);
        UserService service = new UserService(repository, mock(PasswordEncoder.class));

        service.deleteUser("U001");

        verify(repository).deleteById("U001");
    }
}