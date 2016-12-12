package ua.abond.lab4.web.mapper;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.web.support.DefaultPageable;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.config.core.web.support.SortOrder;
import ua.abond.lab4.util.Parse;

import javax.servlet.http.HttpServletRequest;

@Component
public class PageableRequestMapper implements RequestMapper<Pageable> {
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Override
    public DefaultPageable map(HttpServletRequest req) {
        int page = Parse.integerPrimitive(req.getParameter("page"), DEFAULT_PAGE);
        if (page <= 0) {
            page = DEFAULT_PAGE;
        }
        int pageSize = Parse.integerPrimitive(req.getParameter("pageSize"), DEFAULT_PAGE_SIZE);
        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        SortOrder sortOrder = Parse.enumeration(
                SortOrder.class, req.getParameter("order"), SortOrder.ASC
        );
        return new DefaultPageable(page, pageSize, sortOrder);
    }
}
