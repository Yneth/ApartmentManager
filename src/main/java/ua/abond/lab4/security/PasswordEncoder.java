package ua.abond.lab4.security;

public interface PasswordEncoder {
    String encode(String password);

    boolean matches(String rawPassword, String encodedPassword);
}
