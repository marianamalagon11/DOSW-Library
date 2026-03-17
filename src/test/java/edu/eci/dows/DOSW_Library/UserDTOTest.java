package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.controller.dto.UserDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDTOTest {

    @Test
    public void testConstructorGettersAndSetters() {
        UserDTO dto = new UserDTO("U1", "Maria");

        assertEquals("U1", dto.getId());
        assertEquals("Maria", dto.getName());

        dto.setId("U2");
        dto.setName("Ana");

        assertEquals("U2", dto.getId());
        assertEquals("Ana", dto.getName());
    }

    @Test
    public void testEqualsHashCodeAndToString() {
        UserDTO a = new UserDTO("U1", "Maria");
        UserDTO b = new UserDTO("U1", "Maria");
        UserDTO c = new UserDTO("U2", "Ana");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
        assertTrue(a.toString().contains("Maria"));
    }
}

