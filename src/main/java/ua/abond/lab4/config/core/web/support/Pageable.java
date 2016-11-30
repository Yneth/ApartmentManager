package ua.abond.lab4.config.core.web.support;

public interface Pageable {
    int getCurrentPage();

    int getPageSize();

    int getCount();

    SortOrder getOrder();

    String getSortBy();
}
