package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.controller.dto.UserDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDTOTest {

    @Test
    public void testConstructorGettersAndSetters() {
        UserDTO dto = new UserDTO("U1", "Maria", "maria", "USER");

        assertEquals("U1", dto.getId());
        assertEquals("Maria", dto.getName());
        assertEquals("maria", dto.getUsername());
        assertEquals("USER", dto.getRole());

        dto.setId("U2");
        dto.setName("Ana");
        dto.setUsername("ana");
        dto.setRole("LIBRARIAN");

        assertEquals("U2", dto.getId());
        assertEquals("Ana", dto.getName());
        assertEquals("ana", dto.getUsername());
        assertEquals("LIBRARIAN", dto.getRole());
    }

    @Test
    public void testEqualsHashCodeAndToString() {
        UserDTO a = new UserDTO("U1", "Maria", "maria", "USER");
        UserDTO b = new UserDTO("U1", "Maria", "maria", "USER");
        UserDTO c = new UserDTO("U2", "Ana", "ana", "LIBRARIAN");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
        assertTrue(a.toString().contains("Maria"));
    }
}

