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

        assertEquals("Maria", user.getName());
        assertEquals("U1", user.getId());
    }

    @Test
    public void testAllArgsConstructorEqualsHashCodeAndToString() {
        User a = new User("Maria", "U1");
        User b = new User("Maria", "U1");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotNull(a.toString());
        assertTrue(a.toString().contains("Maria"));
    }

    @Test
    public void testEqualsWithDifferentValuesReturnsFalse() {
        User a = new User("Maria", "U1");
        User b = new User("Ana", "U2");

        assertNotEquals(a, b);
    }
}

