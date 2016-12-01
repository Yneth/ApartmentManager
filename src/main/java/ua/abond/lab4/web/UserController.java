package ua.abond.lab4.web;

import ua.abond.lab4.config.core.annotation.Controller;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.annotation.RequestMapping;
import ua.abond.lab4.config.core.web.support.RequestMethod;
import ua.abond.lab4.dao.ApartmentTypeDAO;
import ua.abond.lab4.domain.ApartmentType;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.RequestService;
import ua.abond.lab4.service.UserService;
import ua.abond.lab4.service.exception.ServiceException;
import ua.abond.lab4.web.mapper.ApartmentRequestRequestMapper;
import ua.abond.lab4.web.mapper.UserSessionRequestMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Inject
    private UserService userService;
    @Inject
    private RequestService requestService;
    @Inject
    private ApartmentTypeDAO apartmentTypeDAO;

    @RequestMapping("/requests")
    public void viewOrders(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserSessionRequestMapper().map(req);
        if (!isAuthorized(user)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        List<Request> userRequests = requestService.getUserRequests(user.getId());
        req.setAttribute("requests", userRequests);

        req.getRequestDispatcher("requests.jsp").forward(req, resp);
    }

    @RequestMapping("/request")
    public void viewOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserSessionRequestMapper().map(req);
        if (!isAuthorized(user)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Long id = Long.parseLong(req.getParameter("id"));
        requestService.getById(id).ifPresent(order -> {
            req.setAttribute("request", order);
        });
        req.getRequestDispatcher("request.jsp").forward(req, resp);
    }

    @RequestMapping("/request/new")
    public void getCreateOrderPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserSessionRequestMapper().map(req);
        if (!isAuthorized(user)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        List<ApartmentType> list = apartmentTypeDAO.list();
        req.setAttribute("apartmentTypes", list);
        req.getRequestDispatcher("create-request.jsp").forward(req, resp);
    }

    @RequestMapping(value = "/request/new", method = RequestMethod.POST)
    public void createRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserSessionRequestMapper().map(req);
        if (!isAuthorized(user)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Request request = new ApartmentRequestRequestMapper().map(req);
        request.setUser(user);
        requestService.createRequest(request);
        resp.sendRedirect("/user/requests");
    }

    @RequestMapping(value = "/request/reject", method = RequestMethod.POST)
    public void rejectRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserSessionRequestMapper().map(req);
        if (!isAuthorized(user)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Long id = Long.parseLong(req.getParameter("id"));
        try {
            requestService.rejectRequest(id, null);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("/user/requests");
    }

    private boolean isAuthorized(User user) {
        return user != null && user.getAuthority() != null
                && user.getAuthority().getName() != null &&
                user.getAuthority().getName().equalsIgnoreCase("USER");
    }
}
