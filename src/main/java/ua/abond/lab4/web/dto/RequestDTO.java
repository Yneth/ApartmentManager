package ua.abond.lab4.web.dto;

import java.time.LocalDateTime;

public class RequestDTO {
    private Long id;
    private Long userId;
    private int roomCount;
    private Long apartmentTypeId;
    private LocalDateTime from;
    private LocalDateTime to;
    private String statusComment;

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public Long getApartmentTypeId() {
        return apartmentTypeId;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public String getStatusComment() {
        return statusComment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public void setApartmentTypeId(Long apartmentTypeId) {
        this.apartmentTypeId = apartmentTypeId;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public void setStatusComment(String statusComment) {
        this.statusComment = statusComment;
    }
}
