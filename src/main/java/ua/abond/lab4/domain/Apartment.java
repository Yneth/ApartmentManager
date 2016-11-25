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
}
