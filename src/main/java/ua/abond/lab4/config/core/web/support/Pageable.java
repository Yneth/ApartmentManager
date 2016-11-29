package ua.abond.lab4.config.core.web.support;

public interface Pageable {
    int currentPage();

    int pageSize();

    int count();

    Sort sort();

    String sortBy();
}
