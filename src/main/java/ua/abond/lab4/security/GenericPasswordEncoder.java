package ua.abond.lab4.security;

public class GenericPasswordEncoder implements PasswordEncoder {
    private int iterations;
    private String algorithm;

    @Override
    public String encode(String password) {
        return null;
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return false;
    }
}
