package ua.abond.lab4.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Order extends Entity<Long> {
    private Apartment apartment;
    private Request request;
    private BigDecimal price;
    private boolean payed;

    public Order() {
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return isPayed() == order.isPayed() &&
                Objects.equals(getApartment(), order.getApartment()) &&
                Objects.equals(getRequest(), order.getRequest()) &&
                Objects.equals(getPrice(), order.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getApartment(), getRequest(), getPrice(), isPayed());
    }
}
