package ua.abond.lab4.core.web.method;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RunWith(MockitoJUnitRunner.class)
public class ExceptionHandlerDataTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    @Test(expected = NullPointerException.class)
    public void testConstructorWithFirstArgNull() {
        new ExceptionHandlerData(null, null, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithSecondArgNull() {
        new ExceptionHandlerData(request, null, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithThirdArgNull() {
        new ExceptionHandlerData(request, response, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorWithFourthArgNull() {
        new ExceptionHandlerData(request, response, ExceptionHandlerData.class.getMethods()[0], null);
    }


}