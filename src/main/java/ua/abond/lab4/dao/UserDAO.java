package ua.abond.lab4.dao;

import ua.abond.lab4.domain.User;

import java.util.Optional;

public interface UserDAO extends DAO<User> {
    Optional<User> getByName(String name);
}
