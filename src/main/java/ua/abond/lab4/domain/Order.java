package ua.abond.lab4.domain;

import ua.abond.lab4.web.dto.ConfirmRequestDTO;

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
        if (this == o)
            return true;
        if (!(o instanceof Order))
            return false;
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

    public static class Builder {
        private Request request;
        private Apartment apartment;
        private BigDecimal price;

        public Builder() {

        }

        public Builder setApartmentId(Long id) {
            apartment = new Apartment();
            apartment.setId(id);
            return this;
        }

        public Builder setRequestId(Long id) {
            request = new Request();
            request.setId(id);
            return this;
        }

        public Builder setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Order buildFrom(ConfirmRequestDTO dto) {
            setApartmentId(dto.getApartmentId());
            setRequestId(dto.getRequestId());
            setPrice(dto.getPrice());
            return build();
        }

        public Order build() {
            Order result = new Order();
            result.setApartment(apartment);
            result.setRequest(request);
            result.setPrice(price);
            return result;
        }
    }
}
