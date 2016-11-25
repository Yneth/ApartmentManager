package ua.abond.lab4.util.jdbc;

public class KeyHolder {
    private Object value;

    public Number getKey() {
        if (!(value instanceof Number)) {
            throw new RuntimeException();
        }
        return (Number) value;
    }

    public void setKey(Object value) {
        this.value = value;
    }
}
