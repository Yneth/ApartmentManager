package ua.abond.lab4.dao;

import java.util.Optional;

public interface DAO<T> {
    void create(T entity);
    Optional<T> getById(Long id);
    void update(T entity);
    void deleteById(Long id);
}
