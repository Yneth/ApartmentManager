package ua.abond.lab4.web.validation;

import org.junit.Before;
import org.junit.Test;
import ua.abond.lab4.web.dto.LoginDTO;

import java.util.List;

import static org.junit.Assert.*;

public class LoginDTOValidatorTest {
    private LoginDTOValidator validator;

    @Before
    public void setUp() {
        validator = new LoginDTOValidator();
    }

    @Test
    public void testWithEmptyLoginDTO() {
        LoginDTO loginDTO = new LoginDTO();
        List<String> validate = validator.validate(loginDTO);
        assertNotNull(validate);
        assertEquals(2, validate.size());
    }

    @Test
    public void testSuccessfulValidation() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin("1213");
        loginDTO.setPassword("321321");
        List<String> validate = validator.validate(loginDTO);
        assertNotNull(validate);
        assertTrue(validate.isEmpty());
    }
}