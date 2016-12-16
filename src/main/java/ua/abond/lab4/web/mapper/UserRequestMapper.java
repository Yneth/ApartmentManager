package ua.abond.lab4.web.mapper;

import ua.abond.lab4.core.annotation.Component;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.util.Parse;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserRequestMapper implements RequestMapper<User> {

    @Override
    public User map(HttpServletRequest req) {
        Long id = Parse.longObject(req.getParameter("id"));
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");

        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }
}
