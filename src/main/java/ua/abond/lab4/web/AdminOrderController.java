package ua.abond.lab4.web;

import ua.abond.lab4.core.annotation.Inject;
import ua.abond.lab4.core.web.annotation.Controller;
import ua.abond.lab4.core.web.annotation.OnException;
import ua.abond.lab4.core.web.annotation.RequestMapping;
import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.core.web.support.RequestMethod;
import ua.abond.lab4.domain.Order;
import ua.abond.lab4.service.OrderService;
import ua.abond.lab4.service.RequestMapperService;
import ua.abond.lab4.service.ValidationService;
import ua.abond.lab4.util.Parse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class AdminOrderController {
    public static final String ORDERS_VIEW = "/WEB-INF/pages/admin/orders.jsp";

    @Inject
    private RequestMapperService mapperService;
    @Inject
    private ValidationService validationService;
    @Inject
    private OrderService orderService;

    @RequestMapping("/orders")
    public void viewOrders(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Pageable pageable = mapperService.map(req, Pageable.class);
        Page<Order> page = orderService.list(pageable);

        req.setAttribute("page", page);
        req.getRequestDispatcher(ORDERS_VIEW).forward(req, resp);
    }

    @OnException(value = "/admin/orders")
    @RequestMapping(value = "/order/pay", method = RequestMethod.POST)
    public void payOrder(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Long id = Parse.longObject(req.getParameter("id"));
        orderService.payOrder(id);
        resp.sendRedirect("/admin/orders");
    }
}
