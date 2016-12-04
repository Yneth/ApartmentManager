package ua.abond.lab4.dao;

import ua.abond.lab4.config.core.web.support.Page;
import ua.abond.lab4.config.core.web.support.Pageable;
import ua.abond.lab4.domain.User;

import java.util.Optional;

public interface UserDAO extends DAO<User> {
    Optional<User> getByLogin(String name);

    Page<User> list(Pageable pageable, Long authId);
}
