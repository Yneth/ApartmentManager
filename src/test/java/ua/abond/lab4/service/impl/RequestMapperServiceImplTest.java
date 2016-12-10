package ua.abond.lab4.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.service.RequestMapperService;
import ua.abond.lab4.service.exception.NoSuchRequestMapperException;
import ua.abond.lab4.web.mapper.RequestMapper;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RequestMapperServiceImplTest {
    @Mock
    private HttpServletRequest request;

    private RequestMapperService service;

    @Before
    public void setUp() {
        service = new RequestMapperServiceImpl();
    }

    @Test(expected = NullPointerException.class)
    public void testRegisterNullClass() {
        service.register(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testRegisterNullRowMapper() {
        service.register(Object.class, null);
    }

    @Test(expected = NoSuchRequestMapperException.class)
    public void testNoSuchRequestMapper() {
        service.map(request, Integer.class);
    }

    @Test(expected = NullPointerException.class)
    public void testMapRequestNull() {
        service.map(null, Integer.class);
    }

    @Test(expected = NullPointerException.class)
    public void testMapClassNull() {
        service.map(request, null);
    }

    @Test
    public void testRegisterAndMapShouldWorkFine() {
        RequestMapper mapper = mock(RequestMapper.class);
        service.register(Integer.class, mapper);

        service.map(request, Integer.class);
        verify(mapper).map(request);
    }
}