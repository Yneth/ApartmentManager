package ua.abond.lab4.web;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import ua.abond.lab4.core.web.method.ExceptionHandlerData;
import ua.abond.lab4.service.exception.ValidationException;

import java.util.Collections;

import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    public void testHandleResourceNotFound() throws Exception {
        controller.handleResourceNotFoundException(handlerData);
        verify(response).sendError(anyInt());
    }

    @Test
    @Ignore
    public void testHandleValidationException() throws Exception {
        when(handlerData.getException()).
                thenReturn(new ValidationException(Collections.emptyList()));

        controller.handleValidationException(handlerData);

        verify(request).setAttribute(eq("errors"), or(isNull(), anyList()));

        verifyForward();
        verify(request).getRequestDispatcher(anyString());
    }
}