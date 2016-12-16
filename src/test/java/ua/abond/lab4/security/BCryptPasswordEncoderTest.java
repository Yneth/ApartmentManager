package ua.abond.lab4.security;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BCryptPasswordEncoderTest {
    private String message;
    private PasswordEncoder encoder;

    @Before
    public void setUp() {
        message = "test";
        encoder = new BCryptPasswordEncoder();
    }

    @Test
    public void testEncodedIsNotEqualToRaw() {
        String message = "1";
        assertNotEquals(message, encoder.encode(message));
    }

    @Test
    public void testMatches() {
        String encoded = encoder.encode(message);
        assertTrue(encoder.matches(message, encoded));
    }

    @Test
    public void testOtherMessageDoesNotMatch() {
        String encoded = encoder.encode(message);
        assertFalse(encoder.matches("something else", encoded));
    }

    @Test
    public void testOtherEncodedDoesNotMatch() {
        assertFalse(encoder.matches(message, encoder.encode("something else")));
    }

    @Test
    public void testNonEnglishCharacters() {
        String message = "йцукенгшщзхъфывапролджэячсмитьбю." +
                "йцукенгшщзхїфівапролджєячсмитьбю.";
        assertTrue(encoder.matches(message, encoder.encode(message)));
    }
}