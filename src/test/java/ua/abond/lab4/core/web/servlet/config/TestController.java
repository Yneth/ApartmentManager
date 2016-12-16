package ua.abond.lab4.core.web.servlet.config;

import ua.abond.lab4.core.web.annotation.Controller;
import ua.abond.lab4.core.web.annotation.RequestMapping;
import ua.abond.lab4.core.web.support.RequestMethod;
import ua.abond.lab4.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class TestController {

    @RequestMapping("/")
    public void get(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/test").forward(req, resp);
    }

    @RequestMapping("/exception")
    public void getException(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, ServiceException {
        throw new ServiceException();
    }

    @RequestMapping("/runtime")
    public void getRuntime(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, ServiceException {
        throw new RuntimeException();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void post(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.sendRedirect("/");
    }
}
