package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserModelTest {

    @Test
    public void testNoArgsConstructorAndSetters() {
        User user = new User();

        user.setName("Maria");
        user.setId("U1");
        user.setUsername("maria");
        user.setRole("USER");

        assertEquals("Maria", user.getName());
        assertEquals("U1", user.getId());
        assertEquals("maria", user.getUsername());
        assertEquals("USER", user.getRole());
    }

    @Test
    public void testAllArgsConstructorEqualsHashCodeAndToString() {
        User a = new User("U1", "Maria", "maria", "USER");
        User b = new User("U1", "Maria", "maria", "USER");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotNull(a.toString());
        assertTrue(a.toString().contains("Maria"));
    }

    @Test
    public void testEqualsWithDifferentValuesReturnsFalse() {
        User a = new User("U1", "Maria", "maria", "USER");
        User b = new User("U2", "Ana", "ana", "LIBRARIAN");

        assertNotEquals(a, b);
    }
}

