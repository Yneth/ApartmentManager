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

@Controller
public class LoginController {
    private static final String HOME_MAPPING = "/";
    private static final String LOGIN_MAPPING = "/login";
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

    @RequestMapping(LOGIN_MAPPING)
    public void getLoginPage(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        UserSessionDTO sessionUser = mapperService.map(req, UserSessionDTO.class);
        if (sessionUser != null) {
            resp.sendRedirect(HOME_MAPPING);
            return;
        }
        req.getRequestDispatcher(LOGIN_VIEW).forward(req, resp);
    }

    @OnException(LOGIN_MAPPING)
    @RequestMapping(value = LOGIN_MAPPING, method = RequestMethod.POST)
    public void login(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        UserSessionDTO sessionUser = mapperService.map(req, UserSessionDTO.class);
        if (sessionUser != null) {
            resp.sendRedirect(HOME_MAPPING);
            return;
        }
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        LoginDTO loginDTO = mapperService.map(req, LoginDTO.class);
        validationService.validate(loginDTO);

        if (userService.isAuthorized(loginDTO)) {
            session = req.getSession();
            User byLogin = userService.findByLogin(loginDTO.getLogin()).orElse(null);
            session.setAttribute("user", new UserSessionDTO(byLogin));
            resp.sendRedirect(HOME_MAPPING);
        } else {
            req.setAttribute("errors", Collections.singletonList("login.wrong.credentials"));
            getLoginPage(req, resp);
        }
    }
}
