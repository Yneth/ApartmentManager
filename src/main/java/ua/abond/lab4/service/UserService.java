package ua.abond.lab4.service;

import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.exception.ServiceException;

import java.util.Optional;

public interface UserService {
    Optional<User> findByLogin(String name);

    void register(User user) throws ServiceException;

    void updateAccount(User user);

    void createAdmin(User user) throws ServiceException;

    Page<User> listAdmins(Pageable pageable) throws ServiceException;

    void deleteAdminById(Long id) throws ServiceException;
}
