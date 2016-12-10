package ua.abond.lab4.web;

import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.web.annotation.Controller;
import ua.abond.lab4.config.core.web.annotation.OnException;
import ua.abond.lab4.config.core.web.annotation.RequestMapping;
import ua.abond.lab4.config.core.web.support.RequestMethod;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.RequestMapperService;
import ua.abond.lab4.service.UserService;
import ua.abond.lab4.service.ValidationService;
import ua.abond.lab4.web.dto.UserSessionDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/register")
public class RegisterController {
    public static final String REGISTER_VIEW = "/WEB-INF/pages/register.jsp";

    @Inject
    private RequestMapperService mapperService;
    @Inject
    private ValidationService validationService;

    private final UserService service;

    @Inject
    public RegisterController(UserService service) {
        this.service = service;
    }

    @RequestMapping
    public void getRegisterPage(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        UserSessionDTO user = mapperService.map(req, UserSessionDTO.class);
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
        UserSessionDTO sessionUser = mapperService.map(req, UserSessionDTO.class);
        if (sessionUser != null) {
            resp.sendRedirect("/");
            return;
        }

        User user = mapperService.map(req, User.class);
        validationService.validate(user);

        service.register(user);
        resp.sendRedirect("/");
    }
}
