package ua.abond.lab4.dao;

import ua.abond.lab4.domain.Authority;

import java.util.Optional;

public interface AuthorityDAO extends DAO<Authority> {
    Optional<Authority> getByName(String name);
}
