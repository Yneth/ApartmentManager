package ua.abond.lab4.web;

import ua.abond.lab4.config.core.annotation.Inject;
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

@RequestMapping("/account")
public class AccountController {
    public static final String ACCOUNT_VIEW = "/WEB-INF/pages/account/account.jsp";

    @Inject
    private RequestMapperService mapperService;
    @Inject
    private ValidationService validationService;
    @Inject
    private UserService userService;

    @RequestMapping
    public void viewAccount(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        UserSessionDTO user = mapperService.map(req, UserSessionDTO.class);

        req.setAttribute("user", user);
        req.getRequestDispatcher(ACCOUNT_VIEW).forward(req, resp);
    }

    @OnException("/account")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void updateAccount(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        User user = mapperService.map(req, User.class);
        userService.updateAccount(user);
        resp.sendRedirect("/account");
    }
}
