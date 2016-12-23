package ua.abond.lab4.core.jdbc;

import org.junit.Test;
import ua.abond.lab4.core.jdbc.exception.InvalidKeyTypeException;

import static org.junit.Assert.assertEquals;

public class KeyHolderTest {

    @Test(expected = InvalidKeyTypeException.class)
    public void testGetKeyWhenWrongKeyType() {
        KeyHolder keyHolder = new KeyHolder();
        keyHolder.setKey(new Object());
        keyHolder.getKey();
    }

    @Test
    public void testGetKey() {
        Integer value = 1;
        KeyHolder keyHolder = new KeyHolder();
        keyHolder.setKey(value);
        assertEquals(value, keyHolder.getKey());
    }
}