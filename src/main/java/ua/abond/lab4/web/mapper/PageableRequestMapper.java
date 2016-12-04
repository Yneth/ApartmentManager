package ua.abond.lab4.web.mapper;

import ua.abond.lab4.config.core.web.support.DefaultPageable;
import ua.abond.lab4.config.core.web.support.SortOrder;
import ua.abond.lab4.util.Parse;

import javax.servlet.http.HttpServletRequest;

public class PageableRequestMapper implements RequestMapper<DefaultPageable> {
    @Override
    public DefaultPageable map(HttpServletRequest req) {
        int page = Parse.integerPrimitive(req.getParameter("page"), 1);
        int pageSize = Parse.integerPrimitive(req.getParameter("pageSize"), 10);
        SortOrder sortOrder = Parse.enumeration(SortOrder.class, req.getParameter("order"), SortOrder.ASC);
        return new DefaultPageable(page, pageSize, sortOrder);
    }
}
