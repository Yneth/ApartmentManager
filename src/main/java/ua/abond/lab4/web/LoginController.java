package ua.abond.lab4.web;

import ua.abond.lab4.core.annotation.Inject;
import ua.abond.lab4.core.web.annotation.Controller;
import ua.abond.lab4.core.web.annotation.OnException;
import ua.abond.lab4.core.web.annotation.RequestMapping;
import ua.abond.lab4.core.web.support.RequestMethod;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.RequestMapperService;
import ua.abond.lab4.service.UserService;
import ua.abond.lab4.service.ValidationService;
import ua.abond.lab4.web.dto.LoginDTO;
import ua.abond.lab4.web.dto.UserSessionDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

@Controller
public class LoginController {
    public static final String LOGIN_VIEW = "/WEB-INF/pages/login.jsp";

    private final UserService userService;
    @Inject
    private RequestMapperService mapperService;
    @Inject
    private ValidationService validationService;

    @Inject
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    public void getLoginPage(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        UserSessionDTO sessionUser = mapperService.map(req, UserSessionDTO.class);
        if (sessionUser != null) {
            resp.sendRedirect("/");
            return;
        }
        req.getRequestDispatcher(LOGIN_VIEW).forward(req, resp);
    }

    @OnException("/login")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        UserSessionDTO sessionUser = mapperService.map(req, UserSessionDTO.class);
        if (sessionUser != null) {
            resp.sendRedirect("/");
            return;
        }
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        LoginDTO loginDTO = mapperService.map(req, LoginDTO.class);
        // TODO add validation
        // TODO
        Optional<User> user = userService.findByLogin(loginDTO.getLogin());
        boolean rightCredentials = user.
                map(User::getPassword).
                map(pwd -> pwd.equals(loginDTO.getPassword())).
                orElse(false);

        if (rightCredentials) {
            session = req.getSession();
            session.setAttribute("user", new UserSessionDTO(user.get()));
            resp.sendRedirect("/");
        } else {
            req.setAttribute("errors", Collections.singletonList("Wrong credentials."));
            getLoginPage(req, resp);
        }
    }
}
