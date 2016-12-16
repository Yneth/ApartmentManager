package ua.abond.lab4.core.web.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultServlet extends HttpServlet {
    private static final String DEFAULT_SERVLET_NAME = "default";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher rd = getServletContext().getNamedDispatcher(DEFAULT_SERVLET_NAME);
        if (rd == null) {
            throw new IllegalStateException("A RequestDispatcher could not be located for the default servlet '" +
                    DEFAULT_SERVLET_NAME + "'");
        }
        rd.forward(req, resp);
    }
}
