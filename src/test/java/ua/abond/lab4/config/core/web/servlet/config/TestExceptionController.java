package ua.abond.lab4.config.core.web.servlet.config;

import ua.abond.lab4.config.core.web.annotation.ExceptionController;
import ua.abond.lab4.config.core.web.annotation.ExceptionHandler;
import ua.abond.lab4.config.core.web.method.ExceptionHandlerData;
import ua.abond.lab4.service.exception.ServiceException;

import javax.servlet.ServletException;
import java.io.IOException;

@ExceptionController
public class TestExceptionController {

    @ExceptionHandler(ServiceException.class)
    public void handleServiceException(ExceptionHandlerData data)
            throws IOException, ServletException {
        data.getRequest().setAttribute("error", "error");
        data.getRequest().getRequestDispatcher("/").forward(data.getRequest(), data.getResponse());
    }

    @ExceptionHandler(RuntimeException.class)
    public void handleRuntimeException(ExceptionHandlerData data)
            throws IOException, ServletException {
        throw new RuntimeException();
    }
}
