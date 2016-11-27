package ua.abond.lab4.domain;

import java.util.Objects;

public class Order extends Entity<Long> {
    private User user;
    private Apartment lookup;
    private int duration;

    public Order() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Apartment getLookup() {
        return lookup;
    }

    public void setLookup(Apartment lookup) {
        this.lookup = lookup;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Order))
            return false;
        Order order = (Order) o;
        return Objects.equals(getUser(), order.getUser()) &&
                Objects.equals(getLookup(), order.getLookup()) &&
                Objects.equals(getDuration(), order.getDuration());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getLookup(), getDuration());
    }

    public static class Builder {
        private int duration;
        private User user;
        private Apartment lookup;

        public Builder createUserFromId(Long id) {
            Objects.requireNonNull(id);
            this.user = new User();
            return this;
        }

        public Builder setApartment(Apartment apartment) {
            Objects.requireNonNull(apartment);
            this.lookup = apartment;
            return this;
        }

        public Builder setDurationInMinutes(int amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException();
            }
            this.duration = amount;
            return this;
        }

        public Order build() {
            Objects.requireNonNull(user);
            Objects.requireNonNull(lookup);

            Order order = new Order();
            order.setUser(user);
            order.setLookup(lookup);
            order.setDuration(duration);
            return order;
        }
    }
}
