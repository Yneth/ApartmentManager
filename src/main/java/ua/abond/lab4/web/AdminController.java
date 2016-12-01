package ua.abond.lab4.web;

import org.apache.log4j.Logger;
import ua.abond.lab4.config.core.annotation.Controller;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.annotation.RequestMapping;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.config.core.web.support.RequestMethod;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.ApartmentService;
import ua.abond.lab4.service.OrderService;
import ua.abond.lab4.service.RequestService;
import ua.abond.lab4.service.exception.ServiceException;
import ua.abond.lab4.util.OptionalConsumer;
import ua.abond.lab4.web.mapper.OrderRequestMapper;
import ua.abond.lab4.web.mapper.PageableRequestMapper;
import ua.abond.lab4.web.mapper.UserSessionRequestMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = Logger.getLogger(AdminController.class);

    @Inject
    private RequestService requestService;
    @Inject
    private ApartmentService apartmentService;
    @Inject
    private OrderService orderService;

    @RequestMapping(value = "/request/confirm", method = RequestMethod.POST)
    public void confirmRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserSessionRequestMapper().map(req);
        if (!isAuthorized(user)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Order order = new OrderRequestMapper().map(req);
        try {
            orderService.confirmRequest(order);
        } catch (ServiceException e) {
            logger.debug(e);
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("request.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect("/admin/requests");
    }

    @RequestMapping("/request")
    public void getRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserSessionRequestMapper().map(req);
        if (!isAuthorized(user)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        Long id = Long.parseLong(req.getParameter("id"));
        Pageable pageable = new PageableRequestMapper().map(req);
        OptionalConsumer.of(requestService.getById(id)).
                ifPresent(request -> {
                    req.setAttribute("order", request);
                    req.setAttribute(
                            "appropriateApartments",
                            apartmentService.listMostAppropriate(pageable, request)
                    );
                }).
                ifNotPresent(() -> {
                    req.setAttribute("errorMessage", "Could not find such order.");
                });
        req.getRequestDispatcher("request.jsp").forward(req, resp);
    }

    @RequestMapping("/requests")
    public void getRequests(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserSessionRequestMapper().map(req);
        if (!isAuthorized(user)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Pageable pageable = new PageableRequestMapper().map(req);
        List<Request> list = requestService.list(pageable);

        req.setAttribute("orders", list);
        req.setAttribute("pageable", pageable);
        req.getRequestDispatcher("requests.jsp").forward(req, resp);
    }

    @RequestMapping("/apartments")
    public void getApartments(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new UserSessionRequestMapper().map(req);
        if (!isAuthorized(user)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Pageable pageable = new PageableRequestMapper().map(req);
        List<Apartment> list = apartmentService.list(pageable);

        req.setAttribute("apartments", list);
        req.setAttribute("pageable", pageable);
        req.getRequestDispatcher("apartments.jsp").forward(req, resp);
    }

    private boolean isAuthorized(User user) {
        return user != null
                && user.getAuthority() != null
                && user.getAuthority().getName() != null
                && user.getAuthority().getName().equalsIgnoreCase("ADMIN");
    }
}
