package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.controller.dto.UserDTO;
import edu.eci.dows.tdd.controller.mapper.UserMapper;
import edu.eci.dows.tdd.core.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

    @Test
    public void testToDTOMapsAllFields() {
        User user = new User("U1", "Maria", "maria", "USER");

        UserDTO dto = UserMapper.toDTO(user);

        assertEquals("U1", dto.getId());
        assertEquals("Maria", dto.getName());
        assertEquals("maria", dto.getUsername());
        assertEquals("USER", dto.getRole());
    }

    @Test
    public void testToModelMapsAllFields() {
        UserDTO dto = new UserDTO("U2", "Ana", "ana", "LIBRARIAN");

        User user = UserMapper.toModel(dto);

        assertEquals("U2", user.getId());
        assertEquals("Ana", user.getName());
        assertEquals("ana", user.getUsername());
        assertEquals("LIBRARIAN", user.getRole());
    }
}

