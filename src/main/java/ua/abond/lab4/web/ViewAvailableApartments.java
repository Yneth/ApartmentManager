package ua.abond.lab4.web;

import ua.abond.lab4.service.ApartmentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewAvailableApartments extends HttpServlet {
    private final ApartmentService apartmentService;

    public ViewAvailableApartments(ApartmentService apartmentService) {
        this.apartmentService = apartmentService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
