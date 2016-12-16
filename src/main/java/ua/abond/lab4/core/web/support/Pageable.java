package ua.abond.lab4.core.web.support;

public interface Pageable {
    int getOffset();

    int getPageNumber();

    int getPageSize();

    SortOrder getSortOrder();

    boolean hasPrevious();

    Pageable first();

    Pageable next();

    Pageable previousOrFirst();
}
