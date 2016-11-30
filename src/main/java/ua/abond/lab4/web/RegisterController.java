package ua.abond.lab4.web;

import ua.abond.lab4.config.core.annotation.Controller;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.annotation.RequestMapping;
import ua.abond.lab4.config.core.web.support.RequestMethod;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.UserService;
import ua.abond.lab4.service.exception.ServiceException;
import ua.abond.lab4.web.mapper.UserRequestMapper;
import ua.abond.lab4.web.mapper.UserSessionRequestMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Inject
    private UserService service;

    @RequestMapping
    public void getRegisterPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserSessionRequestMapper().map(req);
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void register(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserRequestMapper().map(req);
        try {
            service.register(user);
        } catch (ServiceException e) {
            // TODO add logging
            e.printStackTrace();
        }

        resp.sendRedirect("/");
    }
}
