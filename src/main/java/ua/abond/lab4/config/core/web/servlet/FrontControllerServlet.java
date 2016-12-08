package ua.abond.lab4.config.core.web.servlet;

import org.apache.log4j.Logger;
import ua.abond.lab4.config.core.ConfigurableBeanFactory;
import ua.abond.lab4.config.core.web.annotation.Controller;
import ua.abond.lab4.config.core.web.annotation.ExceptionController;
import ua.abond.lab4.config.core.web.annotation.ExceptionHandler;
import ua.abond.lab4.config.core.web.annotation.RequestMapping;
import ua.abond.lab4.config.core.web.exception.RequestMappingHandlerException;
import ua.abond.lab4.config.core.web.method.*;
import ua.abond.lab4.config.core.web.support.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class FrontControllerServlet extends BeanFactoryAwareServlet {
    private static final Logger logger = Logger.getLogger(FrontControllerServlet.class);

    private Map<HandlerMethodInfo, HandlerMethod> mappingHandlers;
    private Map<ExceptionHandlerInfo, ExceptionHandlerMethod> exceptionHandlers;

    @Override
    protected void doDispatch(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String requestURI = req.getRequestURI();

        RequestMethod method = RequestMethod.valueOf(req.getMethod());
        HandlerMethodInfo key = new HandlerMethodInfo(requestURI, method);

        HandlerMethod handlerMethod = mappingHandlers.get(key);
        if (handlerMethod == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try {
            handlerMethod.handle(req, resp);
        } catch (RequestMappingHandlerException e) {
            ExceptionHandlerData data = new ExceptionHandlerData(
                    req, resp,
                    handlerMethod.getMethod(),
                    e.getCause().getClass()
            );
            if (!handleException(data)) {
                defaultErrorHandle(requestURI, resp, e);
            }
        }
    }

    private boolean handleException(ExceptionHandlerData data) throws IOException {
        HttpServletRequest req = data.getRequest();
        HttpServletResponse resp = data.getResponse();
        ExceptionHandlerInfo info = new ExceptionHandlerInfo(data.getException());
        if (exceptionHandlers.containsKey(info)) {
            ExceptionHandlerMethod exceptionHandler = exceptionHandlers.get(info);
            try {
                Object result = exceptionHandler.invoke(data);
                if (result instanceof HandlerMethodInfo) {
                    HandlerMethod handler = mappingHandlers.get(result);
                    handler.handle(data.getRequest(), data.getResponse());
                }
            } catch (InvocationTargetException | RequestMappingHandlerException e) {
                defaultErrorHandle(req.getRequestURI(), resp, e);
            }
            return true;
        }
        return false;
    }

    private void defaultErrorHandle(String uri, HttpServletResponse resp, Exception e)
            throws IOException {
        logger.error("Failed to invoke '" + uri + "' handler", e);
        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void onRefreshed(ConfigurableBeanFactory beanFactory) {
        super.onRefreshed(beanFactory);
        initializeMappingHandlers(beanFactory);
        initializeExceptionHandlers(beanFactory);
    }

    private void initializeMappingHandlers(ConfigurableBeanFactory beanFactory) {
        this.mappingHandlers = new HashMap<>();
        Collection<Object> controllers = beanFactory.getBeansWithAnnotation(Controller.class).values();

        for (Object controller : controllers) {
            String prefix = Optional.ofNullable(controller.getClass().getAnnotation(RequestMapping.class)).
                    map(RequestMapping::value).
                    orElse("");
            Arrays.stream(controller.getClass().getDeclaredMethods()).
                    filter(m -> m.isAnnotationPresent(RequestMapping.class)).
                    forEach(m -> addHandler(prefix, controller, m));
        }
        this.mappingHandlers = Collections.unmodifiableMap(this.mappingHandlers);
    }

    private void addHandler(String prefix, Object declaringObj, Method method) {
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        String url = prefix + annotation.value();

        logger.debug("Creating handler for url: " + url + " for " + annotation.method() + " method.");

        HandlerMethodInfo info = new HandlerMethodInfo(url, annotation.method());
        HandlerMethod handlerMethod = new HandlerMethod(declaringObj, method);
        this.mappingHandlers.put(info, handlerMethod);
    }

    private void initializeExceptionHandlers(ConfigurableBeanFactory beanFactory) {
        this.exceptionHandlers = new HashMap<>();
        Collection<Object> objects = beanFactory.getBeansWithAnnotation(ExceptionController.class).values();

        for (Object handler : objects) {
            Arrays.stream(handler.getClass().getDeclaredMethods()).
                    filter(m -> m.isAnnotationPresent(ExceptionHandler.class)).
                    forEach(m -> addExceptionHandler(handler, m));
        }
        this.exceptionHandlers = Collections.unmodifiableMap(exceptionHandlers);
    }

    private void addExceptionHandler(Object declaringObj, Method method) {
        ExceptionHandler annotation = method.getAnnotation(ExceptionHandler.class);
        Class<? extends Throwable>[] values = annotation.value();
        Arrays.stream(values).
                map(ExceptionHandlerInfo::new).
                forEach(info -> exceptionHandlers.put(info, new ExceptionHandlerMethod(method, declaringObj)));
    }
}
