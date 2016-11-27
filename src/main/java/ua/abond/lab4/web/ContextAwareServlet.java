package ua.abond.lab4.web;

import ua.abond.lab4.config.core.ApplicationContext;
import ua.abond.lab4.config.core.exception.ApplicationContextException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ContextAwareServlet extends HttpServlet {
    private Class<?> contextClass;
    private String contextConfigLocation;

    private ApplicationContext context;

//    private MappingTranslatorResolver mappingTranslatorResolver;
//    private MappingResolver mappingResolver;
//    private ViewResolver viewResolver;

    public ContextAwareServlet() {
    }

    @Override
    public void init() throws ServletException {
        if (context == null) {

        }
    }

    private ApplicationContext createContext() {
        Class<?> contextClass = getContextClass();
        if (!ApplicationContext.class.isAssignableFrom(contextClass)) {
            throw new ApplicationContextException();
        }
        ApplicationContext ac = (ApplicationContext) instantiate(contextClass);
//        ac.setConfigLocation(contextConfigLocation);

        prepare(ac);

        return ac;
    }

    private void prepare(ApplicationContext ac) {
        ac.prepare();
    }

    private <T> T instantiate(Class<T> type) {
        try {
            return type.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp);
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) {

    }

    public Class<?> getContextClass() {
        return contextClass;
    }

    public void setContextClass(Class<?> contextClass) {
        this.contextClass = contextClass;
    }

    public String getContextConfigLocation() {
        return contextConfigLocation;
    }

    public void setContextConfigLocation(String contextConfigLocation) {
        this.contextConfigLocation = contextConfigLocation;
    }
}
