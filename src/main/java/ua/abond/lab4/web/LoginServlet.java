package ua.abond.lab4.web;

import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.dao.jdbc.JdbcAuthorityDAO;
import ua.abond.lab4.dao.jdbc.JdbcUserDAO;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.UserService;
import ua.abond.lab4.service.impl.UserServiceImpl;
import ua.abond.lab4.util.DataSourceProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Inject
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO remove this code from here
        DataSource ds = DataSourceProvider.getInstance().getDataSource();
        UserService userService =
                new UserServiceImpl(new JdbcUserDAO(ds), new JdbcAuthorityDAO(ds));

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        Optional<User> user = userService.findByName(login);
        boolean rightCredentials = user.
                map(User::getPassword).
                map(pwd -> pwd.equals(password)).
                orElse(false);

        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        if (rightCredentials) {
            session = req.getSession();
            session.setAttribute("user", user.get());
            session.setAttribute("authorities", user.get().getAuthority().getName());
        }
        resp.sendRedirect("/");
    }
}
