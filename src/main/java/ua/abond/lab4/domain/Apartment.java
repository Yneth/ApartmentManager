package ua.abond.lab4.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Apartment extends Entity<Long> {
    private String name;
    private int roomCount;
    private BigDecimal price;
    private ApartmentType type;

    public Apartment() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public ApartmentType getType() {
        return type;
    }

    public void setType(ApartmentType type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Apartment))
            return false;
        Apartment apartment = (Apartment) o;
        return getRoomCount() == apartment.getRoomCount() &&
                Objects.equals(getName(), apartment.getName()) &&
                Objects.equals(getPrice(), apartment.getPrice()) &&
                Objects.equals(getType(), apartment.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getRoomCount(), getPrice(), getType());
    }
}
