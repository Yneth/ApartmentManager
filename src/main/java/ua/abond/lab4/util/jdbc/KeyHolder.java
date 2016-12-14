package ua.abond.lab4.util.jdbc;

import ua.abond.lab4.util.jdbc.exception.InvalidKeyTypeException;

public class KeyHolder {
    private Object value;

    public Number getKey() {
        if (!(value instanceof Number)) {
            throw new InvalidKeyTypeException("Expected 'Number' was: '" + value.getClass() + "'");
        }
        return (Number) value;
    }

    public void setKey(Object value) {
        this.value = value;
    }
}
