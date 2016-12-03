package ua.abond.lab4.config.core.web.servlet;

import org.apache.log4j.Logger;
import ua.abond.lab4.config.core.ConfigurableBeanFactory;
import ua.abond.lab4.config.core.context.AnnotationBeanFactory;
import ua.abond.lab4.config.core.exception.BeanFactoryException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BeanFactoryAwareServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(BeanFactoryAwareServlet.class);
    private static final String CONTEXT_CONFIG_LOCATION_ATTRIBUTE_NAME = "contextConfigLocation";

    private Class<?> contextClass;
    private String contextConfigLocation;

    private ConfigurableBeanFactory beanFactory;

    public BeanFactoryAwareServlet() {
    }

    @Override
    public void init() throws ServletException {
        if (beanFactory == null) {
            try {
                beanFactory = new AnnotationBeanFactory(getContextConfigLocation());
            } catch (BeanFactoryException e) {
                logger.error("Failed to create bean factory.", e);
                throw e;
            }
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
