package ua.abond.lab4.util.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public final class Validation<T, E> {
    private final T value;
    private List<E> exceptions;

    public Validation(T value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public Validation<T, E> validate(Predicate<T> predicate, E message) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(message);
        if (!predicate.test(value)) {
            getExceptions().add(message);
        }
        return this;
    }

    public <V> Validation<T, E> validate(Function<T, V> f, Predicate<V> predicate, E message) {
        Objects.requireNonNull(f);
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(message);
        return validate(f.andThen(predicate::test)::apply, message);
    }

    public boolean isValid() {
        return exceptions == null || exceptions.isEmpty();
    }

    public List<E> getExceptions() {
        if (exceptions == null) {
            exceptions = new ArrayList<>();
        }
        return exceptions;
    }

    public static <T, E> Reader<T, Validation<T, E>> reader() {
        return new Reader<T, Validation<T, E>>(Validation::new);
    }
}
