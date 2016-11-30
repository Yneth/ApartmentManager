package ua.abond.lab4.config.core.web.support;

public class DefaultPageable implements Pageable {
    private Integer page;
    private String sortBy;
    private SortOrder sortOrder;

    public DefaultPageable(Integer page, String sortBy, SortOrder sortOrder) {
        this.page = page;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
    }

    @Override
    public int getCurrentPage() {
        return page;
    }

    @Override
    public int getPageSize() {
        return 0;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public SortOrder getOrder() {
        return sortOrder;
    }

    @Override
    public String getSortBy() {
        return sortBy;
    }
}
