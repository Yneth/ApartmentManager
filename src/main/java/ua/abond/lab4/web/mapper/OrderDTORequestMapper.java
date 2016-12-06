package ua.abond.lab4.web.mapper;

import ua.abond.lab4.util.Parse;
import ua.abond.lab4.web.dto.OrderDTO;

import javax.servlet.http.HttpServletRequest;

public class OrderDTORequestMapper implements RequestMapper<OrderDTO> {

    @Override
    public OrderDTO map(HttpServletRequest req) {
        OrderDTO order = new OrderDTO();
        order.setId(Parse.longValue(req.getParameter("id")));
        order.setApartmentId(Parse.longValue(req.getParameter("apartmentId")));
        order.setRequestId(Parse.longValue(req.getParameter("requestId")));
        order.setPrice(Parse.bigDecimal(req.getParameter("price")));
        return order;
    }
}
