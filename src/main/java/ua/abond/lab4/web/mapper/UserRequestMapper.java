package ua.abond.lab4.web.mapper;

import ua.abond.lab4.domain.User;

import javax.servlet.http.HttpServletRequest;

public class UserRequestMapper implements RequestMapper<User> {
    @Override
    public User map(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }
}
