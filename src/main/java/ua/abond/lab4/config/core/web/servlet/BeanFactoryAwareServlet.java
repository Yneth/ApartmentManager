package ua.abond.lab4.config.core.web.servlet;

import org.apache.log4j.Logger;
import ua.abond.lab4.config.core.ConfigurableBeanFactory;
import ua.abond.lab4.config.core.context.AnnotationBeanFactory;
import ua.abond.lab4.config.core.web.exception.ApplicationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BeanFactoryAwareServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(BeanFactoryAwareServlet.class);
    private static final String CONTEXT_CLASS_ATTR = "contextClass";
    private static final String CONTEXT_CONFIG_LOCATION_ATTR = "contextConfigLocation";
    private static final Class<?> DEFAULT_CONTEXT_CLASS = AnnotationBeanFactory.class;

    private Class<?> contextClass = DEFAULT_CONTEXT_CLASS;
    private String contextConfigLocation;

    private ConfigurableBeanFactory beanFactory;

    public BeanFactoryAwareServlet() {
    }

    @Override
    public void init() throws ServletException {
        loadContextClass();
        if (beanFactory == null) {
            try {
                beanFactory = createBeanFactory();
                if (beanFactory instanceof AnnotationBeanFactory) {
                    ((AnnotationBeanFactory) beanFactory).scan(getContextConfigLocation());
                }
                beanFactory.refresh();
            } catch (Exception e) {
                logger.error("Failed to create bean factory.", e);
                throw e;
            }
            onRefreshed(beanFactory);
        }
    }

    private void loadContextClass() {
        String className = getInitParameter(CONTEXT_CLASS_ATTR);
        try {
            if (className != null) {
                contextClass = Class.forName(className);
            }
        } catch (ClassNotFoundException e) {
            throw new ApplicationException("Failed to find class with name '" + className + "'.", e);
        }
    }

    private ConfigurableBeanFactory createBeanFactory() {
        if (!ConfigurableBeanFactory.class.isAssignableFrom(contextClass)) {
            throw new ApplicationException("Given contextClass does not inherit BeanFactory interface");
        }
        try {
            return (ConfigurableBeanFactory) contextClass.newInstance();
        } catch (InstantiationException e) {
            throw new ApplicationException("Given contextClass is abstract.", e);
        } catch (IllegalAccessException e) {
            throw new ApplicationException("Given contextClass has non public constructor.", e);
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

    protected abstract void doDispatch(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException;

    public Class<?> getContextClass() {
        return contextClass;
    }

    public void setContextClass(Class<?> contextClass) {
        this.contextClass = contextClass;
    }

    public String getContextConfigLocation() {
        if (contextConfigLocation == null) {
            contextConfigLocation = getInitParameter(CONTEXT_CONFIG_LOCATION_ATTR);
        }
        return contextConfigLocation;
    }

    public void setContextConfigLocation(String contextConfigLocation) {
        this.contextConfigLocation = contextConfigLocation;
    }
}
