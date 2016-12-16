package ua.abond.lab4.web;

import ua.abond.lab4.core.annotation.Inject;
import ua.abond.lab4.core.web.annotation.Controller;
import ua.abond.lab4.core.web.annotation.OnException;
import ua.abond.lab4.core.web.annotation.RequestMapping;
import ua.abond.lab4.core.web.support.Page;
import ua.abond.lab4.core.web.support.Pageable;
import ua.abond.lab4.core.web.support.RequestMethod;
import ua.abond.lab4.domain.Apartment;
import ua.abond.lab4.domain.Request;
import ua.abond.lab4.service.ApartmentService;
import ua.abond.lab4.service.RequestMapperService;
import ua.abond.lab4.service.RequestService;
import ua.abond.lab4.service.ValidationService;
import ua.abond.lab4.util.Parse;
import ua.abond.lab4.web.dto.ConfirmRequestDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class AdminRequestController {
    public static final String REQUEST_VIEW = "/WEB-INF/pages/admin/request.jsp";
    public static final String REQUESTS_VIEW = "/WEB-INF/pages/admin/requests.jsp";

    @Inject
    private RequestMapperService mapperService;
    @Inject
    private ValidationService validationService;
    @Inject
    private RequestService requestService;
    @Inject
    private ApartmentService apartmentService;

    @OnException(value = "/admin/request")
    @RequestMapping(value = "/request/confirm", method = RequestMethod.POST)
    public void confirmRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ConfirmRequestDTO dto = mapperService.map(req, ConfirmRequestDTO.class);
        validationService.validate(dto);

        requestService.confirmRequest(dto);
        resp.sendRedirect("/admin/requests");
    }

    @RequestMapping("/request")
    public void viewRequest(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Long id = Parse.longObject(req.getParameter("id"));
        Pageable pageable = mapperService.map(req, Pageable.class);

        Request request = requestService.getById(id);
        Page<Apartment> apartmentPage = apartmentService.listMostAppropriate(pageable, request);
        req.setAttribute("request", request);
        req.setAttribute("page", apartmentPage);

        req.getRequestDispatcher(REQUEST_VIEW).forward(req, resp);
    }

    @RequestMapping(value = "/request/delete", method = RequestMethod.POST)
    public void deleteRequest(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Long id = Parse.longObject(req.getParameter("id"));
        requestService.deleteById(id);
        resp.sendRedirect("/admin/requests");
    }

    @RequestMapping("/requests")
    public void viewRequests(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        Pageable pageable = mapperService.map(req, Pageable.class);
        Page<Request> page = requestService.list(pageable);

        req.setAttribute("page", page);
        req.getRequestDispatcher(REQUESTS_VIEW).forward(req, resp);
    }
}
