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
import ua.abond.lab4.service.ApartmentService;
import ua.abond.lab4.service.OrderService;
import ua.abond.lab4.service.RequestService;
import ua.abond.lab4.service.exception.ServiceException;
import ua.abond.lab4.util.OptionalConsumer;
import ua.abond.lab4.web.dto.ConfirmRequestDTO;
import ua.abond.lab4.web.mapper.ConfirmRequestDTORequestMapper;
import ua.abond.lab4.web.mapper.PageableRequestMapper;
import ua.abond.lab4.web.validation.ConfirmRequestDTOValidator;

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
        ConfirmRequestDTO dto = new ConfirmRequestDTORequestMapper().map(req);
        List<String> errors = new ConfirmRequestDTOValidator().validate(dto);
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("request.jsp").forward(req, resp);
            return;
        }

        try {
            orderService.confirmRequest(dto);
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
        Long id = Long.parseLong(req.getParameter("id"));
        Pageable pageable = new PageableRequestMapper().map(req);
        OptionalConsumer.of(requestService.getById(id)).
                ifPresent(request -> {
                    req.setAttribute("request", request);
                    req.setAttribute(
                            "apartments",
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
        Pageable pageable = new PageableRequestMapper().map(req);
        List<Request> list = requestService.list(pageable);

        req.setAttribute("requests", list);
        req.setAttribute("pageable", pageable);
        req.getRequestDispatcher("requests.jsp").forward(req, resp);
    }

    @RequestMapping("/apartments")
    public void getApartments(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Pageable pageable = new PageableRequestMapper().map(req);
        List<Apartment> list = apartmentService.list(pageable);

        req.setAttribute("apartments", list);
        req.setAttribute("pageable", pageable);
        req.getRequestDispatcher("apartments.jsp").forward(req, resp);
    }

    @RequestMapping("/orders")
    public void getOrders(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Pageable pageable = new PageableRequestMapper().map(req);
        List<Order> list = orderService.list(pageable);

        req.setAttribute("orders", list);
        req.setAttribute("pageable", pageable);
        req.getRequestDispatcher("orders.jsp").forward(req, resp);
    }
}
