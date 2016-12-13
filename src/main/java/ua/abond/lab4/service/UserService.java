package ua.abond.lab4.service;

import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.exception.ServiceException;
import ua.abond.lab4.web.dto.ChangePasswordDTO;
import ua.abond.lab4.web.dto.LoginDTO;

import java.util.Optional;

public interface UserService {
    User getById(Long id) throws ServiceException;

    Optional<User> findByLogin(String name);

    boolean isAuthorized(LoginDTO login);

    void register(User user) throws ServiceException;

    void updateAccount(User user) throws ServiceException;

    void changePassword(Long id, ChangePasswordDTO dto) throws ServiceException;

    void createAdmin(User user) throws ServiceException;

    Page<User> listAdmins(Pageable pageable) throws ServiceException;

    void deleteAdminById(Long id) throws ServiceException;
}
