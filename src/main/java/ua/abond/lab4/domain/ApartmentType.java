package ua.abond.lab4.domain;

import java.util.Objects;

public class ApartmentType extends Entity<Long> {
    private String name;

    public ApartmentType() {
    }

    public ApartmentType(Long id, String name) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);

        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ApartmentType))
            return false;
        ApartmentType that = (ApartmentType) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
