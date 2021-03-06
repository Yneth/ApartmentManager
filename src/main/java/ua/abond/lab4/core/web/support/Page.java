package ua.abond.lab4.core.web.support;

import java.util.List;

public interface Page<T> {
    List<T> getContent();

    int getSize();

    boolean hasContent();

    long getTotalPages();

    long getTotalElements();
}
