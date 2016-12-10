package ua.abond.lab4.web.mapper;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.web.dto.LoginDTO;

import javax.servlet.http.HttpServletRequest;

@Component
public class LoginDTORequestMapper implements RequestMapper<LoginDTO> {

    @Override
    public LoginDTO map(HttpServletRequest req) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin(req.getParameter("login"));
        loginDTO.setPassword(req.getParameter("password"));
        return loginDTO;
    }
}
