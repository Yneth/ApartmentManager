package ua.abond.lab4.domain;

import java.util.Objects;

public class Authority extends Entity<Long> {
    private String name;

    public Authority() {
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
        if (!(o instanceof Authority))
            return false;
        Authority that = (Authority) o;
        return Objects.equals(getName(), that.getName());

    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
