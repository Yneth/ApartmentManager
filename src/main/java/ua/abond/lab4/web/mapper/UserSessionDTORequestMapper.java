package ua.abond.lab4.web.mapper;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.web.dto.UserSessionDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class UserSessionDTORequestMapper implements RequestMapper<UserSessionDTO> {

    @Override
    public UserSessionDTO map(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            return (UserSessionDTO) session.getAttribute("user");
        }
        return null;
    }
}
