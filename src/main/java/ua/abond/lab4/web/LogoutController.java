package ua.abond.lab4.web;

import ua.abond.lab4.core.web.annotation.Controller;
import ua.abond.lab4.core.web.annotation.RequestMapping;
import ua.abond.lab4.core.web.support.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect("/");
    }
}
