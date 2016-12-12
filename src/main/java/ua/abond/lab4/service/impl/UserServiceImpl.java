package ua.abond.lab4.service.impl;

import ua.abond.lab4.config.core.annotation.Component;
import ua.abond.lab4.config.core.annotation.Inject;
import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.dao.AuthorityDAO;
import ua.abond.lab4.dao.UserDAO;
import ua.abond.lab4.domain.Authority;
import ua.abond.lab4.domain.User;
import ua.abond.lab4.service.UserService;
import ua.abond.lab4.service.exception.LoginIsAlreadyTakenException;
import ua.abond.lab4.service.exception.ResourceNotFoundException;
import ua.abond.lab4.service.exception.ServiceException;
import ua.abond.lab4.web.dto.LoginDTO;
import ua.abond.lab4.web.dto.UserSessionDTO;

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
    public User getById(Long id) throws ServiceException {
        return userDAO.getById(id).
                orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Optional<User> findByLogin(String name) {
        return userDAO.getByLogin(name);
    }

    @Override
    public boolean isAuthorized(LoginDTO login) {
        return false;
    }

    @Override
    public void register(User user) throws ServiceException {
        createUserWithAuth(user, "USER");
    }

    @Override
    public void updateAccount(UserSessionDTO user) throws ServiceException {
        User userToUpdate = getById(user.getId());
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userDAO.update(userToUpdate);
    }

    @Override
    public void createAdmin(User user) throws ServiceException {
        createUserWithAuth(user, "ADMIN");
    }

    @Override
    public Page<User> listAdmins(Pageable pageable) throws ServiceException {
        Authority authority = authorityDAO.getByName("ADMIN").orElse(null);
        if (authority == null) {
            throw new ServiceException("Failed to get ADMIN authority.");
        }
        return userDAO.list(pageable, authority.getId());
    }

    @Override
    public void deleteAdminById(Long id) throws ServiceException {
        userDAO.getById(id).
                filter(user -> "ADMIN".equals(user.getAuthority().getName())).
                map(user -> {
                    userDAO.deleteById(id);
                    return user;
                }).orElseThrow(ResourceNotFoundException::new);
    }

    private void createUserWithAuth(User user, String authName)
            throws ServiceException {
        User existing = userDAO.getByLogin(user.getLogin()).orElse(null);
        if (existing != null) {
            throw new LoginIsAlreadyTakenException();
        }
        authorityDAO.getByName(authName).
                map(auth -> {
                    user.setAuthority(auth);
                    userDAO.create(user);
                    return user;
                }).
                orElseThrow(() -> new ServiceException(String.format("Failed to get '%s' authority.", authName)));
    }
}
