package ua.abond.lab4.web;

import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.web.annotation.Controller;
import ua.abond.lab4.config.core.web.annotation.OnException;
import ua.abond.lab4.config.core.web.annotation.RequestMapping;
import ua.abond.lab4.config.core.web.support.RequestMethod;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.UserService;
import ua.abond.lab4.web.mapper.UserRequestMapper;
import ua.abond.lab4.web.mapper.UserSessionRequestMapper;
import ua.abond.lab4.web.validation.UserValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {
    public static final String REGISTER_VIEW = "/WEB-INF/pages/register.jsp";

    private final UserService service;

    @Inject
    public RegisterController(UserService service) {
        this.service = service;
    }

    @RequestMapping
    public void getRegisterPage(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        User user = new UserSessionRequestMapper().map(req);
        if (user != null) {
            resp.sendRedirect("/");
            return;
        }
        req.getRequestDispatcher(REGISTER_VIEW).forward(req, resp);
    }

    @OnException(value = "/register")
    @RequestMapping(method = RequestMethod.POST)
    public void register(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        User sessionUser = new UserSessionRequestMapper().map(req);
        if (sessionUser != null) {
            resp.sendRedirect("/");
            return;
        }

        User user = new UserRequestMapper().map(req);
        List<String> errors = new UserValidator().validate(user);
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher(REGISTER_VIEW).forward(req, resp);
            return;
        }

        service.register(user);
        resp.sendRedirect("/");
    }
}
