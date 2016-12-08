package ua.abond.lab4.web;

import ua.abond.lab4.config.core.web.ExceptionHandlerData;
import ua.abond.lab4.config.core.web.annotation.ExceptionHandler;
import ua.abond.lab4.config.core.web.annotation.OnException;
import ua.abond.lab4.service.exception.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.ResourceBundle;

@ua.abond.lab4.config.core.web.annotation.ExceptionController
public class ExceptionController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public void handleResourceNotFoundException(HttpServletRequest req,
                                                HttpServletResponse resp,
                                                ExceptionHandlerData data)
            throws IOException {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @ExceptionHandler(RequestConfirmException.class)
    public void handleRequestConfirmException(HttpServletRequest req,
                                              HttpServletResponse resp,
                                              ExceptionHandlerData e)
            throws IOException, ServletException {
        localizedHandle("request.error.confirm", req, resp, e);
    }


    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public void handleResourceAlreadyExistsException(HttpServletRequest req,
                                                     HttpServletResponse resp,
                                                     ExceptionHandlerData e)
            throws IOException, ServletException {
        localizedHandle("order.error.payed", req, resp, e);
    }

    @ExceptionHandler(OrderAlreadyPayedException.class)
    public void handleOrderAlreadyPayedException(HttpServletRequest req,
                                                 HttpServletResponse resp,
                                                 ExceptionHandlerData e)
            throws IOException, ServletException {
        localizedHandle("order.error.payed", req, resp, e);
    }

    @ExceptionHandler(RejectRequestException.class)
    public void handRejectRequestException(HttpServletRequest req,
                                           HttpServletResponse resp,
                                           ExceptionHandlerData e)
            throws ServletException, IOException {
        localizedHandle("request.error.reject", req, resp, e);
    }

    private void localizedHandle(String errorKey,
                                 HttpServletRequest req,
                                 HttpServletResponse resp,
                                 ExceptionHandlerData data)
            throws IOException, ServletException {
        String forward = getForward(data);
        if (forward != null) {
            setLocalizedError(req, errorKey);
            req.getRequestDispatcher(forward).forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private String getForward(ExceptionHandlerData data) {
        Method handler = data.getHandler();
        if (handler.isAnnotationPresent(OnException.class)) {
            OnException annotation = handler.getAnnotation(OnException.class);
            String forward = annotation.forward();
            if (!"".equals(forward)) {
                return forward;
            }
        }
        return null;
    }

    private void setLocalizedError(HttpServletRequest req, String key) {
        String lang = req.getParameter("lang");
        setError(req, getBundle(lang).getString(key));
    }

    private void setError(HttpServletRequest req, String message) {
        req.setAttribute("errors", Collections.singletonList(message));
    }

    private ResourceBundle getBundle(String lang) {
        return ResourceBundle.getBundle(lang);
    }
}
