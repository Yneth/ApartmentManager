package ua.abond.lab4.web.mapper;

import ua.abond.lab4.core.annotation.Component;
import ua.abond.lab4.web.dto.ChangePasswordDTO;

import javax.servlet.http.HttpServletRequest;

@Component
public class ChangePasswordDTORequestMapper implements RequestMapper<ChangePasswordDTO> {

    @Override
    public ChangePasswordDTO map(HttpServletRequest req) {
        ChangePasswordDTO dto = new ChangePasswordDTO();
        dto.setOldPassword(req.getParameter("oldPassword"));
        dto.setNewPassword(req.getParameter("newPassword"));
        dto.setNewPasswordCopy(req.getParameter("newPasswordCopy"));
        return dto;
    }
}
