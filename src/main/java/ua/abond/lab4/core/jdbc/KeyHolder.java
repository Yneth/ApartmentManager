package ua.abond.lab4.core.jdbc;

import ua.abond.lab4.core.jdbc.exception.InvalidKeyTypeException;

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
