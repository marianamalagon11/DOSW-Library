package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserModelTest {

    @Test
    public void testNoArgsConstructorAndSetters() {
        User user = new User();

        user.setId("U1");
        user.setName("Maria");
        user.setUsername("maria");
        user.setPassword("secret");
        user.setRole("USER");
        user.setEmail("maria@test.com");
        user.setMembershipType("PREMIUM");
        user.setAddedAt("2026-04-01T00:00:00Z");

        assertEquals("Maria", user.getName());
        assertEquals("U1", user.getId());
        assertEquals("maria", user.getUsername());
        assertEquals("secret", user.getPassword());
        assertEquals("USER", user.getRole());
        assertEquals("maria@test.com", user.getEmail());
        assertEquals("PREMIUM", user.getMembershipType());
        assertEquals("2026-04-01T00:00:00Z", user.getAddedAt());
    }

    @Test
    public void testAllArgsConstructorEqualsHashCodeAndToString() {
        User a = new User("U1", "Maria", "maria", "secret", "USER", "maria@test.com", "PREMIUM", "2026-04-01T00:00:00Z");
        User b = new User("U1", "Maria", "maria", "secret", "USER", "maria@test.com", "PREMIUM", "2026-04-01T00:00:00Z");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotNull(a.toString());
        assertTrue(a.toString().contains("Maria"));
    }

    @Test
    public void testEqualsWithDifferentValuesReturnsFalse() {
        User a = new User("U1", "Maria", "maria", "secret", "USER", null, null, null);
        User b = new User("U2", "Ana", "ana", "secret", "LIBRARIAN", null, null, null);

        assertNotEquals(a, b);
    }
}

