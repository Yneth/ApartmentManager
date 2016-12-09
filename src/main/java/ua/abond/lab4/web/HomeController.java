package ua.abond.lab4.web;

import ua.abond.lab4.config.core.web.annotation.Controller;
import ua.abond.lab4.config.core.web.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {
    public static final String HOME_VIEW = "/WEB-INF/pages/index.jsp";

    @RequestMapping("/")
    public void getIndexPage(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        req.getRequestDispatcher(HOME_VIEW).forward(req, resp);
    }
}
