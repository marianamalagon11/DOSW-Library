package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.core.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    @Test
    public void testAddUserAndGetUser() {
        UserService s = new UserService();
        User u = new User("Mari", "U001");
        s.addUser(u);
        assertEquals(u, s.getUserById("U001"));
    }

    @Test
    public void testGetAllUsers() {
        UserService s = new UserService();
        User u1 = new User("Shawn", "U002");
        User u2 = new User("Joji", "U003");
        s.addUser(u1);
        s.addUser(u2);
        assertEquals(2, s.getAllUsers().size());
        assertTrue(s.getAllUsers().contains(u2));
    }

    @Test
    public void testLombokGetterAndSetterForUsers() {
        UserService service = new UserService();
        List<User> customUsers = new ArrayList<>();
        customUsers.add(new User("Ana", "U10"));

        service.setUsers(customUsers);

        assertEquals(customUsers, service.getUsers());
        assertEquals(1, service.getUsers().size());
    }

    @Test
    public void testLombokEqualsHashCodeAndToStringForUserService() {
        UserService a = new UserService();
        UserService b = new UserService();

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotNull(a.toString());
        assertTrue(a.toString().contains("users"));
    }

    @Test
    public void testGetUserByIdWhenNotFoundReturnsNull() {
        UserService s = new UserService();
        s.addUser(new User("Mari", "U001"));

        assertNull(s.getUserById("U404"));
    }
}