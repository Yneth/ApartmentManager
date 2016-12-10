package ua.abond.lab4.web.mapper;

import javax.servlet.http.HttpServletRequest;

public interface RequestMapper<T> {
    T map(HttpServletRequest req);
}
