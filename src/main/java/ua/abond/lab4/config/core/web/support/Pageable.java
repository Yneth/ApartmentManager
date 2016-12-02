package ua.abond.lab4.config.core.web.support;

public interface Pageable {
    int getOffset();

    int getPageNumber();

    int getPageSize();

    SortOrder getSortOrder();

    String sortBy();

    boolean hasPrevious();

    Pageable first();

    Pageable next();

    Pageable previousOrFirst();
}
