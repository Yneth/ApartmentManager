package ua.abond.lab4.web.dto;

import ua.abond.lab4.domain.Apartment;

import java.math.BigDecimal;

public class ConfirmRequestDTO {
    private Long orderId;
    private BigDecimal price;
    private Apartment apartment;

    public ConfirmRequestDTO() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setLookup(Apartment apartment) {
        this.apartment = apartment;
    }
}
