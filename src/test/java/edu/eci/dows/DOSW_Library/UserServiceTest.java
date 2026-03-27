package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.controller.dto.CreateUserRequestDTO;
import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.core.service.UserService;
import edu.eci.dows.tdd.persistence.entity.UserEntity;
import edu.eci.dows.tdd.persistence.entity.enums.UserRole;
import edu.eci.dows.tdd.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Test
    public void testCreateUserReturnsSavedUser() {
        UserRepository repository = mock(UserRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        UserService service = new UserService(repository, passwordEncoder);

        CreateUserRequestDTO request = new CreateUserRequestDTO("U001", "Mari", "mari", "1234", "USER");

        UserEntity saved = new UserEntity();
        saved.setId("U001");
        saved.setName("Mari");
        saved.setUsername("mari");
        saved.setRole(UserRole.USER);
        when(repository.existsByUsername("mari")).thenReturn(false);
        when(passwordEncoder.encode("1234")).thenReturn("encoded");
        when(repository.save(any(UserEntity.class))).thenReturn(saved);

        User result = service.createUser(request);
        assertEquals("U001", result.getId());
        assertEquals("Mari", result.getName());
        assertEquals("mari", result.getUsername());
        assertEquals("USER", result.getRole());
    }

    @Test
    public void testGetAllUsers() {
        UserRepository repository = mock(UserRepository.class);
        UserService service = new UserService(repository, mock(PasswordEncoder.class));

        UserEntity u1 = new UserEntity();
        u1.setId("U002");
        u1.setName("Shawn");
        u1.setUsername("shawn");
        u1.setRole(UserRole.USER);
        UserEntity u2 = new UserEntity();
        u2.setId("U003");
        u2.setName("Joji");
        u2.setUsername("joji");
        u2.setRole(UserRole.LIBRARIAN);

        when(repository.findAll()).thenReturn(List.of(u1, u2));

        List<User> users = service.getAllUsers();
        assertEquals(2, users.size());
        assertEquals("U003", users.get(1).getId());
    }

    @Test
    public void testGetUserByIdWhenFound() {
        UserRepository repository = mock(UserRepository.class);
        UserService service = new UserService(repository, mock(PasswordEncoder.class));

        UserEntity entity = new UserEntity();
        entity.setId("U001");
        entity.setName("Mari");
        entity.setUsername("mari");
        entity.setRole(UserRole.USER);
        when(repository.findById("U001")).thenReturn(Optional.of(entity));

        Optional<User> result = service.getUserById("U001");
        assertTrue(result.isPresent());
        assertEquals("Mari", result.get().getName());
    }

    @Test
    public void testGetUserByIdWhenNotFoundReturnsEmpty() {
        UserRepository repository = mock(UserRepository.class);
        UserService service = new UserService(repository, mock(PasswordEncoder.class));

        when(repository.findById("U404")).thenReturn(Optional.empty());

        assertTrue(service.getUserById("U404").isEmpty());
    }

    @Test
    public void testDeleteUserDelegatesToRepository() {
        UserRepository repository = mock(UserRepository.class);
        UserService service = new UserService(repository, mock(PasswordEncoder.class));

        service.deleteUser("U001");

        verify(repository).deleteById("U001");
    }
}