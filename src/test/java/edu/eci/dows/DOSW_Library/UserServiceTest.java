package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.model.User;
import edu.eci.dows.tdd.service.UserService;
import org.junit.jupiter.api.Test;

public class UserServiceTest {
    @Test
    public void testAddUser() {
        UserService s = new UserService();
        User u = new User("Nombre", "ID");
        s.addUser(u);
    }
}