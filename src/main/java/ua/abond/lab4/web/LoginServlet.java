package ua.abond.lab4.web;

import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private final UserService userService;

    public LoginServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = (String) req.getAttribute("login");
        String password = (String) req.getAttribute("password");

        boolean rightCredentials = userService.findByName(login).
                map(User::getPassword).
                map(pwd -> pwd.equals(password)).
                orElse(false);

        if (rightCredentials) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            session = req.getSession();
            session.setAttribute("login", login);
            session.setAttribute("password", password);
        }

    }
}
