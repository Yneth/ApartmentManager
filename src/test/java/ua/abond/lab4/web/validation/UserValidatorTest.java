package ua.abond.lab4.web.validation;

import org.junit.Test;
import ua.abond.lab4.domain.User;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserValidatorTest {

    @Test
    public void testEmptyUser() {
        User user = new User();
        List<String> validate = new UserValidator().validate(user);
        assertEquals(2, validate.size());
    }

    @Test
    public void testSuccessfulValidation() {
        User user = new User();
        user.setLogin("Login1111");
        user.setPassword("Password");
        List<String> validate = new UserValidator().validate(user);
        assertTrue(validate.isEmpty());
    }

    @Test
    public void testShortPassword() {
        User user = new User();
        user.setLogin("Login1111");
        user.setPassword("Pass");
        List<String> validate = new UserValidator().validate(user);
        assertEquals(1, validate.size());
    }

    @Test
    public void testShortLogin() {
        User user = new User();
        user.setLogin("Login");
        user.setPassword("Password");
        List<String> validate = new UserValidator().validate(user);
        assertEquals(1, validate.size());
    }
}