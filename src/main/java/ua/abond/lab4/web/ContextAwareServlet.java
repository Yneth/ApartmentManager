package ua.abond.lab4.web;

import ua.abond.lab4.config.core.BeanFactory;
import ua.abond.lab4.config.core.context.AnnotationBeanFactory;
import ua.abond.lab4.config.core.exception.ApplicationContextException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class ContextAwareServlet extends HttpServlet {
    private static final String CONTEXT_CONFIG_LOCATION_ATTRIBUTE_NAME = "contextConfigLocation";

    private Class<?> contextClass;
    private String contextConfigLocation;

    private BeanFactory beanFactory;

    public ContextAwareServlet() {
    }

    @Override
    public void init() throws ServletException {
        if (beanFactory == null) {
//            beanFactory = createContext();
            beanFactory = new AnnotationBeanFactory(getContextConfigLocation());
        }
    }

    private BeanFactory createContext() {
        Class<?> contextClass = getContextClass();
        if (!BeanFactory.class.isAssignableFrom(contextClass)) {
            throw new ApplicationContextException();
        }
        BeanFactory ac = (BeanFactory) instantiate(contextClass);

        prepare(ac);

        return ac;
    }

    private void prepare(BeanFactory ac) {
        ac.prepare();
    }

    private <T> T instantiate(Class<T> type) {
        try {
            return type.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp);
    }

    protected abstract void doDispatch(HttpServletRequest req, HttpServletResponse resp);

    public Class<?> getContextClass() {
        return contextClass;
    }

    public void setContextClass(Class<?> contextClass) {
        this.contextClass = contextClass;
    }

    public String getContextConfigLocation() {
        if (contextConfigLocation == null) {
            contextConfigLocation = (String)
                    getServletContext().getAttribute(CONTEXT_CONFIG_LOCATION_ATTRIBUTE_NAME);
        }
        return contextConfigLocation;
    }

    public void setContextConfigLocation(String contextConfigLocation) {
        this.contextConfigLocation = contextConfigLocation;
    }
}
