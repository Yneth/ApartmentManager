package ua.abond.lab4.util.validation;

import java.util.function.Function;

public final class Reader<R, A> {
    private final Function<R, A> fn;

    public Reader(Function<R, A> fn) {
        this.fn = fn;
    }

    public A run(R r) {
        return fn.apply(r);
    }

    public <B> Reader<R, B> map(Function<A, B> bc) {
        return new Reader<>(r -> bc.apply(run(r)));
    }

    public <B> Reader<R, B> flatMap(Function<A, Reader<R, B>> bc) {
        return new Reader<>(r -> bc.apply(run(r)).run(r));
    }

    public static <R, A> A run(R r, Reader<R, A> reader) {
        return reader.run(r);
    }

    public static <R, A> Function<Reader<R, A>, A> partialRun(R r) {
        return reader -> reader.run(r);
    }
}
