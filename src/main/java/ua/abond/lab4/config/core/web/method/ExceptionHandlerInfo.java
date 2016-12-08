package ua.abond.lab4.config.core.web.method;

import java.util.Objects;

public class ExceptionHandlerInfo {
    private final Class<? extends Throwable> exception;

    public ExceptionHandlerInfo(Class<? extends Throwable> exception) {
        this.exception = exception;
    }

    public Class<? extends Throwable> getException() {
        return exception;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExceptionHandlerInfo)) return false;
        ExceptionHandlerInfo that = (ExceptionHandlerInfo) o;
        return Objects.equals(getException(), that.getException());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getException());
    }
}
