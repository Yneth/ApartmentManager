package ua.abond.lab4.web.mapper;

import ua.abond.lab4.util.Parse;
import ua.abond.lab4.web.dto.LoginDTO;

import javax.servlet.http.HttpServletRequest;

public class LoginDTORequestMapper implements RequestMapper<LoginDTO> {
    @Override
    public LoginDTO map(HttpServletRequest req) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin(Parse.string(req.getParameter("login")));
        loginDTO.setPassword(Parse.string(req.getParameter("password")));
        return loginDTO;
    }
}
