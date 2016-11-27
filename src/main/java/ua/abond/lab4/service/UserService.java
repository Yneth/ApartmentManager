package ua.abond.lab4.service;

import ua.abond.lab4.domain.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByName(String name);

    void register(User user) throws ServiceException;
    void updateAccount(User user);
    void changePassword(String newPassword);
}
