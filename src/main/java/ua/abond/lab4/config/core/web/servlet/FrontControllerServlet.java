package ua.abond.lab4.config.core.web.servlet;

import org.apache.log4j.Logger;
import ua.abond.lab4.config.core.ConfigurableBeanFactory;
import ua.abond.lab4.config.core.annotation.Controller;
import ua.abond.lab4.config.core.annotation.RequestMapping;
import ua.abond.lab4.config.core.web.HandlerMethod;
import ua.abond.lab4.config.core.web.HandlerMethodInfo;
import ua.abond.lab4.config.core.web.support.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class FrontControllerServlet extends ContextAwareServlet {
    private static final Logger logger = Logger.getLogger(FrontControllerServlet.class);

    private final Map<HandlerMethodInfo, HandlerMethod> handlers = new HashMap<>();

    @Override
    protected void doDispatch(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String requestURI = req.getRequestURI();

        RequestMethod method = RequestMethod.valueOf(req.getMethod());
        HandlerMethodInfo key = new HandlerMethodInfo(requestURI, method);

        HandlerMethod handlerMethod = handlers.get(key);
        if (handlerMethod == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            try {
                handlerMethod.handle(req, resp);
            } catch (InvocationTargetException e) {
                logger.error("Failed to invoke '" + requestURI + "' handler", e);
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    protected void onRefreshed(ConfigurableBeanFactory beanFactory) {
        super.onRefreshed(beanFactory);
        initializeHandlers(beanFactory);
    }

    private void initializeHandlers(ConfigurableBeanFactory beanFactory) {
        Collection<Object> controllers = beanFactory.getBeansWithAnnotation(Controller.class).values();

        for (Object controller : controllers) {
            String prefix = Optional.ofNullable(controller.getClass().getAnnotation(RequestMapping.class)).
                    map(RequestMapping::value).
                    orElse("");
            Arrays.stream(controller.getClass().getDeclaredMethods()).
                    filter(m -> m.isAnnotationPresent(RequestMapping.class)).
                    forEach(m -> addHandler(prefix, controller, m));
        }
    }

    private void addHandler(String prefix, Object declaringClass, Method method) {
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        String url = prefix + annotation.value();

        logger.debug("Creating handler for url: " + url + " for " + annotation.method() + " method.");

        handlers.put(new HandlerMethodInfo(url, annotation.method()), new HandlerMethod(declaringClass, method));
    }
}
