package ua.abond.lab4.config.core.web.servlet;

import ua.abond.lab4.config.core.ConfigurableBeanFactory;
import ua.abond.lab4.config.core.annotation.Controller;
import ua.abond.lab4.config.core.annotation.RequestMapping;
import ua.abond.lab4.config.core.web.HandlerMethod;
import ua.abond.lab4.config.core.web.HandlerMethodInfo;
import ua.abond.lab4.config.core.web.support.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

public class FrontControllerServlet extends ContextAwareServlet {
    private final Map<HandlerMethodInfo, HandlerMethod> handlers = new HashMap<>();

    @Override
    protected void doDispatch(HttpServletRequest req, HttpServletResponse resp) {
        String requestURI = req.getRequestURI();

        RequestMethod method = RequestMethod.valueOf(req.getMethod());
        HandlerMethodInfo key = new HandlerMethodInfo(requestURI, method);

        HandlerMethod handlerMethod = handlers.get(key);
        if (handlerMethod == null) {
            try {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            handlerMethod.handle(req, resp);
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
        handlers.put(new HandlerMethodInfo(url, annotation.method()), new HandlerMethod(declaringClass, method));
    }
}
