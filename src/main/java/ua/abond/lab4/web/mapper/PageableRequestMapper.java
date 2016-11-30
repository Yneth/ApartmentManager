package ua.abond.lab4.web.mapper;

import ua.abond.lab4.config.core.web.support.DefaultPageable;
import ua.abond.lab4.config.core.web.support.SortOrder;
import ua.abond.lab4.util.Parse;

import javax.servlet.http.HttpServletRequest;

public class PageableRequestMapper implements RequestMapper<DefaultPageable> {
    @Override
    public DefaultPageable map(HttpServletRequest req) {
        Integer page = Parse.integer(req.getParameter("page"));
        String sortBy = req.getParameter("sortBy");
        SortOrder sortOrder = Parse.enumeration(SortOrder.class, req.getParameter("order"), SortOrder.ASC);
        return new DefaultPageable(page, sortBy, sortOrder);
    }
}
