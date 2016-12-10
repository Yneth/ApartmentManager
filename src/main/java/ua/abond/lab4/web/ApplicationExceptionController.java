package ua.abond.lab4.web;

import ua.abond.lab4.config.core.web.annotation.ExceptionController;
import ua.abond.lab4.config.core.web.annotation.ExceptionHandler;
import ua.abond.lab4.config.core.web.annotation.OnException;
import ua.abond.lab4.config.core.web.method.ExceptionHandlerData;
import ua.abond.lab4.config.core.web.method.HandlerMethodInfo;
import ua.abond.lab4.config.core.web.support.RequestMethod;
import ua.abond.lab4.service.exception.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@ExceptionController
public class ApplicationExceptionController {

    @ExceptionHandler(ValidationException.class)
    public HandlerMethodInfo handleValidationException(ExceptionHandlerData data) {
        ValidationException exception = (ValidationException) data.getException();
        List<String> errors = exception.getErrors();
        data.getRequest().setAttribute("errors", errors);
        return getForward(data);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public void handleResourceNotFoundException(ExceptionHandlerData data)
            throws Exception {
        data.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @ExceptionHandler(RequestConfirmException.class)
    public HandlerMethodInfo handleRequestConfirmException(ExceptionHandlerData data)
            throws Exception {
        localizedHandle("request.error.confirm", data);
        return getForward(data);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public HandlerMethodInfo handleResourceAlreadyExistsException(ExceptionHandlerData data)
            throws Exception {
        localizedHandle("error.already.exists", data);
        return getForward(data);
    }

    @ExceptionHandler(LoginIsAlreadyTakenException.class)
    public HandlerMethodInfo handleLoginIsAlreadyTakenException(ExceptionHandlerData data)
            throws Exception {
        localizedHandle("login.error.login.taken", data);
        return getForward(data);
    }

    @ExceptionHandler(OrderAlreadyPayedException.class)
    public HandlerMethodInfo handleOrderAlreadyPayedException(ExceptionHandlerData data)
            throws Exception {
        localizedHandle("order.error.payed", data);
        return getForward(data);
    }

    @ExceptionHandler(RejectRequestException.class)
    public HandlerMethodInfo handleRejectRequestException(ExceptionHandlerData data)
            throws Exception {
        localizedHandle("request.error.reject", data);
        return getForward(data);
    }

    private void localizedHandle(String errorKey, ExceptionHandlerData data)
            throws Exception {
        HandlerMethodInfo forward = getForward(data);
        if (forward != null) {
            HttpServletRequest req = data.getRequest();
            setLocalizedError(req, errorKey);
        }
    }

    private HandlerMethodInfo getForward(ExceptionHandlerData data) {
        Method handler = data.getHandler();
        if (handler.isAnnotationPresent(OnException.class)) {
            OnException annotation = handler.getAnnotation(OnException.class);
            String forward = annotation.value();
            RequestMethod method = annotation.method();
            if (!"".equals(forward)) {
                return new HandlerMethodInfo(forward, method);
            }
        }
        return null;
    }

    private void setLocalizedError(HttpServletRequest req, String key) {
        String lang = (String) req.getAttribute("lang");
        setError(req, getBundle(lang).getString(key));
    }

    private void setError(HttpServletRequest req, String message) {
        req.setAttribute("errors", Collections.singletonList(message));
    }

    private ResourceBundle getBundle(String lang) {
        return ResourceBundle.getBundle("locale", new Locale(lang));
    }
}
