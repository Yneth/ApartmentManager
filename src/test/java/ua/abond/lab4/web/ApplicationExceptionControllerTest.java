package ua.abond.lab4.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.config.core.web.method.ExceptionHandlerData;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationExceptionControllerTest extends ControllerTest {
    private ApplicationExceptionController controller;
    @InjectMocks
    private ExceptionHandlerData handlerData;

    @Before
    public void setUp() {
        controller = new ApplicationExceptionController();
    }

    @Test
    public void testHandleResourceNotFound() {

    }
}