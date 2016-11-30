package ua.abond.lab4.web.mapper;

import ua.abond.lab4.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserSessionRequestMapper implements RequestMapper<User> {

    @Override
    public User map(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            return (User) session.getAttribute("user");
        }
        return null;
    }
}
