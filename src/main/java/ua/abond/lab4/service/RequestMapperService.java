package ua.abond.lab4.service;

import ua.abond.lab4.web.mapper.RequestMapper;

import javax.servlet.http.HttpServletRequest;

public interface RequestMapperService extends ClassRegistry<RequestMapper> {
    <T> T map(HttpServletRequest request, Class<T> object);
}
