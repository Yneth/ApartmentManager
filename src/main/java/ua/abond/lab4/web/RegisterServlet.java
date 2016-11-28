package ua.abond.lab4.web;

import ua.abond.lab4.dao.jdbc.JdbcAuthorityDAO;
import ua.abond.lab4.dao.jdbc.JdbcUserDAO;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.UserService;
import ua.abond.lab4.service.exception.ServiceException;
import ua.abond.lab4.service.impl.UserServiceImpl;
import ua.abond.lab4.util.DataSourceProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@WebServlet(urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO remove this code from here
        DataSource ds = DataSourceProvider.getInstance().getDataSource();
        UserService service =
                new UserServiceImpl(new JdbcUserDAO(ds), new JdbcAuthorityDAO(ds));

        // TODO add validation and object parsing
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        try {
            service.register(user);
        } catch (ServiceException e) {
            // TODO add logging
            e.printStackTrace();
        }

        resp.sendRedirect("/");
    }
}
