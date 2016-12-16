package ua.abond.lab4.core.web.method;

import ua.abond.lab4.core.web.support.RequestMethod;

import java.util.Objects;

public class HandlerMethodInfo {
    private final String url;
    private final RequestMethod method;

    public HandlerMethodInfo(String url, RequestMethod method) {
        Objects.requireNonNull(url);
        Objects.requireNonNull(method);

        this.url = url;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public RequestMethod getMethod() {
        return method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof HandlerMethodInfo))
            return false;
        HandlerMethodInfo that = (HandlerMethodInfo) o;
        return Objects.equals(getUrl(), that.getUrl()) &&
                getMethod() == that.getMethod();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrl(), getMethod());
    }
}
