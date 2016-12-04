package ua.abond.lab4.web;

import ua.abond.lab4.config.core.annotation.Controller;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.annotation.RequestMapping;
import ua.abond.lab4.config.core.web.support.RequestMethod;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.UserService;
import ua.abond.lab4.web.dto.LoginDTO;
import ua.abond.lab4.web.mapper.LoginDTORequestMapper;
import ua.abond.lab4.web.mapper.UserSessionRequestMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Controller
public class LoginController {
    public static final String LOGIN_VIEW = "/WEB-INF/pages/login.jsp";

    private final UserService userService;

    @Inject
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    public void getLoginPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User sessionUser = new UserSessionRequestMapper().map(req);
        if (sessionUser != null) {
            resp.sendRedirect("/");
            return;
        }
        req.getRequestDispatcher(LOGIN_VIEW).forward(req, resp);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        User sessionUser = new UserSessionRequestMapper().map(req);
        if (sessionUser != null) {
            resp.sendRedirect("/");
            return;
        }
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        LoginDTO loginDTO = new LoginDTORequestMapper().map(req);
        // TODO add validation
        // TODO
        Optional<User> user = userService.findByLogin(loginDTO.getLogin());
        boolean rightCredentials = user.
                map(User::getPassword).
                map(pwd -> pwd.equals(loginDTO.getPassword())).
                orElse(false);

        if (rightCredentials) {
            session = req.getSession();
            session.setAttribute("user", user.get());
            resp.sendRedirect("/");
        } else {
            req.setAttribute("errors", Collections.singletonList("Wrong credentials."));
            getLoginPage(req, resp);
        }
    }
}
