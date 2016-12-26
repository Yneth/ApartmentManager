package ua.abond.lab4.web.dto;

import java.time.LocalDate;
import java.util.List;

public class SearchApartmentDTO {
    private String location;
    private Integer roomCount;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private List<Integer> apartmentTypeIds;

    public SearchApartmentDTO() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public List<Integer> getApartmentTypeIds() {
        return apartmentTypeIds;
    }

    public void setApartmentTypeIds(List<Integer> apartmentTypeIds) {
        this.apartmentTypeIds = apartmentTypeIds;
    }
}
