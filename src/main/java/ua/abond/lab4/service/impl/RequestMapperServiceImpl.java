package ua.abond.lab4.service.impl;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.service.RequestMapperService;
import ua.abond.lab4.service.exception.NoSuchRequestMapperException;
import ua.abond.lab4.web.mapper.RequestMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Objects;

@Component
public class RequestMapperServiceImpl extends AbstractClassRegistry<RequestMapper>
        implements RequestMapperService {

    public RequestMapperServiceImpl() {
        super(new HashMap<>());
    }

    @Override
    public <T> T map(HttpServletRequest request, Class<T> type) {
        Objects.requireNonNull(request);
        Objects.requireNonNull(type);
        RequestMapper<T> mapper = getMapper(type);
        if (Objects.isNull(mapper)) {
            throw new NoSuchRequestMapperException("Could not find RequestMapper for '" + type + "'");
        }
        return mapper.map(request);
    }

    @SuppressWarnings("unchecked")
    private <T> RequestMapper<T> getMapper(Class<?> type) {
        if (Objects.isNull(type)) {
            return null;
        }
        if (!registry.containsKey(type)) {
            return getMapper(type.getSuperclass());
        }
        return (RequestMapper<T>) registry.get(type);
    }
}
