package ua.abond.lab4.domain;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Order extends Entity<Long> {
    private User user;
    private Apartment lookup;
    private Duration duration;

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

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public static class Builder {
        private User user;
        private Apartment lookup;
        private Duration duration;

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

        public Builder setDurationInMinutes(long amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException();
            }
            this.duration = Duration.of(amount, ChronoUnit.MINUTES);
            return this;
        }

        public Order build() {
            Objects.requireNonNull(user);
            Objects.requireNonNull(lookup);
            Objects.requireNonNull(duration);

            Order order = new Order();
            order.setUser(user);
            order.setLookup(lookup);
            order.setDuration(duration);
            return order;
        }
    }
}
