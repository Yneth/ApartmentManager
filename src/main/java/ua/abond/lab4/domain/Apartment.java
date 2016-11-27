package ua.abond.lab4.domain;

import java.util.Objects;

public class Apartment extends Entity<Long> {
    private int roomCount;
    private ApartmentType type;

    public Apartment() {
    }

    public Apartment(int roomCount, ApartmentType type) {
        if (roomCount <= 0) {
            throw new IllegalArgumentException("Room count cannot be zero or less");
        }
        Objects.requireNonNull(type);
        this.roomCount = roomCount;
        this.type = type;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Apartment)) return false;
        Apartment apartment = (Apartment) o;
        return getRoomCount() == apartment.getRoomCount() &&
                Objects.equals(getType(), apartment.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoomCount(), getType());
    }
}
