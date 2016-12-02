package ua.abond.lab4.config.core.web.support;

import java.util.List;

public class DefaultPage<T> implements Page<T> {
    private List<T> content;
    private long totalElementCount;
    private Pageable pageable;

    public DefaultPage(List<T> content, long totalElementCount, Pageable pageable) {
        this.content = content;
        this.totalElementCount = totalElementCount;
        this.pageable = pageable;
    }

    @Override
    public List<T> getContent() {
        return content;
    }

    @Override
    public int getSize() {
        if (!hasContent()) {
            return 0;
        }
        return content.size();
    }

    @Override
    public boolean hasContent() {
        return content != null;
    }

    @Override
    public long getTotalPages() {
        return (long) Math.ceil(getTotalElements() / ((double) pageable.getPageSize()));
    }

    @Override
    public long getTotalElements() {
        return totalElementCount;
    }
}
