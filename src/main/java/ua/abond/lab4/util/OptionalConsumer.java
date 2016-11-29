package ua.abond.lab4.util;

import java.util.Optional;
import java.util.function.Consumer;

public final class OptionalConsumer<T> {
    private final Optional<T> optional;

    private OptionalConsumer(Optional<T> optional) {
        this.optional = optional;
    }

    public OptionalConsumer<T> ifPresent(Consumer<T> c) {
        optional.ifPresent(c);
        return this;
    }

    public OptionalConsumer<T> ifNotPresent(Runnable r) {
        if (!optional.isPresent()) {
            r.run();
        }
        return this;
    }

    public Optional<T> get() {
        return optional;
    }

    public static <T> OptionalConsumer<T> of(Optional<T> opt) {
        return new OptionalConsumer<T>(opt);
    }
}
