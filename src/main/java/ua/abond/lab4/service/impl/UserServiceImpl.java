package ua.abond.lab4.service.impl;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.dao.AuthorityDAO;
import ua.abond.lab4.dao.UserDAO;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.UserService;
import ua.abond.lab4.service.exception.ServiceException;

import java.util.Objects;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final AuthorityDAO authorityDAO;

    @Inject
    public UserServiceImpl(UserDAO userDAO, AuthorityDAO authorityDAO) {
        this.userDAO = userDAO;
        this.authorityDAO = authorityDAO;
    }

    @Override
    public Optional<User> findByName(String name) {
        return userDAO.getByLogin(name);
    }

    @Override
    public void register(User user) throws ServiceException {
        Objects.requireNonNull(user, "User cannot be null");

        authorityDAO.getByName("USER").
                map(auth -> {
                    user.setAuthority(auth);
                    userDAO.create(user);
                    return user;
                }).
                orElseThrow(() -> new ServiceException("Failed to get USER authority."));
    }

    @Override
    public void updateAccount(User user) {
        userDAO.update(user);
    }

    @Override
    public void changePassword(String newPassword) {
//        userDAO.update();
    }
}
