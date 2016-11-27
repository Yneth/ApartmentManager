package ua.abond.lab4.util.validation;

import ua.abond.lab4.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public final class Validation<T, E> {
    private final T value;
    private List<E> exceptions;

    public Validation(T value) {
        this.value = value;
    }

    public Validation<T, E> validate(Predicate<T> predicate, E message) {
        if (!predicate.test(value)) {
            getExceptions().add(message);
        }
        return this;
    }

    public <V> Validation<T, E> validate(Function<T, V> f, Predicate<V> predicate, E message) {
        return validate(f.andThen(predicate::test)::apply, message);
    }

    public boolean isValid() {
        return exceptions == null || exceptions.isEmpty();
    }

    private List<E> getExceptions() {
        if (exceptions == null) {
            exceptions = new ArrayList<>();
        }
        return exceptions;
    }

    public static <T, E> Reader<T, Validation<T, E>> reader() {
        return new Reader<T, Validation<T, E>>(Validation::new);
    }

    public static void main(String[] args) {
        Validation.<User, String>reader().map(
                v -> v.validate(User::getPassword, Objects::nonNull, "password cannot be null").
                        validate(User::getLogin, Objects::nonNull, "login cannot be null").
                        validate(User::getLogin, login -> login.length() > 6 && login.length() < 20, "")
        ).run(new User());
        Validation.<User, Exception>reader().map(
                v -> v.validate(User::getPassword, Objects::nonNull, new IllegalArgumentException("password cannot be null"))
        ).run(new User());
    }
}
