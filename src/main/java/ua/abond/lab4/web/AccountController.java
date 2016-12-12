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
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/account")
public class AccountController {
    public static final String ACCOUNT_VIEW = "/WEB-INF/pages/account/account.jsp";

    @Inject
    private UserService userService;
    @Inject
    private RequestMapperService mapperService;
    @Inject
    private ValidationService validationService;

    @RequestMapping
    public void viewAccount(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        if (isLoggedIn(req)) {
            UserSessionDTO user = mapperService.map(req, UserSessionDTO.class);

            req.setAttribute("user", userService.getById(user.getId()));
            req.getRequestDispatcher(ACCOUNT_VIEW).forward(req, resp);
        } else {
            resp.sendRedirect("/login");
        }
    }

    @OnException("/account")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void updateAccount(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        if (isLoggedIn(req)) {
            User user = mapperService.map(req, User.class);
            userService.updateAccount(user);
            resp.sendRedirect("/account");
        } else {
            resp.sendRedirect("/login");
        }
    }

    private Optional<UserSessionDTO> getSessionUser(HttpServletRequest req) {
        return Optional.ofNullable(mapperService.map(req, UserSessionDTO.class));
    }

    private boolean isLoggedIn(HttpServletRequest req)
            throws IOException {
        return getSessionUser(req).isPresent();
    }
}
