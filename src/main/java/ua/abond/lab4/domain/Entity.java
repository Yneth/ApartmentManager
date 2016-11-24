package ua.abond.lab4.domain;

public abstract class Entity<T> {
    protected T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
