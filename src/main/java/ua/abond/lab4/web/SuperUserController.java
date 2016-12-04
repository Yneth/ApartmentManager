package ua.abond.lab4.web;

import ua.abond.lab4.config.core.annotation.Controller;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.annotation.RequestMapping;
import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.config.core.web.support.RequestMethod;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.UserService;
import ua.abond.lab4.service.exception.ServiceException;
import ua.abond.lab4.util.Parse;
import ua.abond.lab4.web.mapper.PageableRequestMapper;
import ua.abond.lab4.web.mapper.UserRequestMapper;
import ua.abond.lab4.web.validation.UserValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/supersu")
public class SuperUserController {
    private static final String ADMINS_VIEW = "/WEB-INF/pages/supersu/admins.jsp";
    private static final String CREATE_ADMIN_VIEW = "/WEB-INF/pages/supersu/create-admin.jsp";

    private final UserService userService;

    @Inject
    public SuperUserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/admins")
    public void getAdminsPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Pageable pageable = new PageableRequestMapper().map(req);

        try {
            Page<User> page = userService.listAdmins(pageable);
            req.setAttribute("admins", page.getContent());
            req.setAttribute("pageable", pageable);
        } catch (ServiceException e) {
            req.setAttribute("errors", Collections.singletonList(e.getMessage()));
        }
        req.getRequestDispatcher(ADMINS_VIEW).forward(req, resp);
    }

    @RequestMapping("/admin/new")
    public void getCreateAdminPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher(CREATE_ADMIN_VIEW).forward(req, resp);
    }

    @RequestMapping(value = "/admin/new", method = RequestMethod.POST)
    public void createAdmin(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserRequestMapper().map(req);
        List<String> errors = new UserValidator().validate(user);
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            getCreateAdminPage(req, resp);
            return;
        }

        try {
            userService.createAdmin(user);
        } catch (ServiceException e) {
            req.setAttribute("errors", Collections.singletonList(e.getMessage()));
            getCreateAdminPage(req, resp);
            return;
        }
        resp.sendRedirect("/");
    }

    @RequestMapping(value = "/admin/delete", method = RequestMethod.POST)
    public void deleteAdmin(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Parse.longValue(req.getParameter("id"));

        try {
            userService.deleteAdminById(id);
        } catch (ServiceException e) {
            req.setAttribute("errors", Collections.singletonList(e.getMessage()));
            getAdminsPage(req, resp);
        }
        resp.sendRedirect("/supersu/admins");
    }
}

