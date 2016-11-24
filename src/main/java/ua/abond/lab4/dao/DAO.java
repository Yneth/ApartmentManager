package ua.abond.lab4.dao;

public interface DAO<T> {
    void create(T entity);
    T getById(Long id);
    void update(T entity);
    void deleteById(Long id);
}
