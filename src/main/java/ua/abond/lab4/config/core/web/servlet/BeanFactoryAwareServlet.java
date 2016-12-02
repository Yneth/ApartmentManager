package ua.abond.lab4.config.core.web.servlet;

import ua.abond.lab4.config.core.ConfigurableBeanFactory;
import ua.abond.lab4.config.core.context.AnnotationBeanFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BeanFactoryAwareServlet extends HttpServlet {
    private static final String CONTEXT_CONFIG_LOCATION_ATTRIBUTE_NAME = "contextConfigLocation";

    private Class<?> contextClass;
    private String contextConfigLocation;

    private ConfigurableBeanFactory beanFactory;

    public BeanFactoryAwareServlet() {
    }

    @Override
    public void init() throws ServletException {
        if (beanFactory == null) {
            beanFactory = new AnnotationBeanFactory(getContextConfigLocation());
            onRefreshed(beanFactory);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doDispatch(req, resp);
    }

    protected void onRefreshed(ConfigurableBeanFactory beanFactory) {

    }

    protected abstract void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    public Class<?> getContextClass() {
        return contextClass;
    }

    public void setContextClass(Class<?> contextClass) {
        this.contextClass = contextClass;
    }

    public String getContextConfigLocation() {
        if (contextConfigLocation == null) {
            contextConfigLocation = getInitParameter(CONTEXT_CONFIG_LOCATION_ATTRIBUTE_NAME);
        }
        return contextConfigLocation;
    }

    public void setContextConfigLocation(String contextConfigLocation) {
        this.contextConfigLocation = contextConfigLocation;
    }
}
