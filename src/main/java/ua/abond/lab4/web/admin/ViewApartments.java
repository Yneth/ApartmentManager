package ua.abond.lab4.web.admin;

import ua.abond.lab4.service.ApartmentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewApartments extends HttpServlet {
    private final ApartmentService apartmentService;

    public ViewApartments(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
