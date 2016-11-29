package ua.abond.lab4.service;

import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.exception.ServiceException;

import java.util.Optional;

public interface UserService {
    Optional<User> findByLogin(String name);

    void register(User user) throws ServiceException;
    void updateAccount(User user);
    void changePassword(String newPassword);
}
