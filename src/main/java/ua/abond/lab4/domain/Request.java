package ua.abond.lab4.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Request extends Entity<Long> {
    private User user;
    private Apartment lookup;
    private LocalDateTime from;
    private LocalDateTime to;
    private String statusComment;
    private RequestStatus status = RequestStatus.CREATED;

    public Request() {
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

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public String getStatusComment() {
        return statusComment;
    }

    public void setStatusComment(String statusComment) {
        this.statusComment = statusComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request)) return false;
        Request request = (Request) o;
        return Objects.equals(getUser(), request.getUser()) &&
                Objects.equals(getLookup(), request.getLookup()) &&
                Objects.equals(getFrom(), request.getFrom()) &&
                Objects.equals(getTo(), request.getTo()) &&
                getStatus() == request.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getLookup(), getFrom(), getTo(), getStatus());
    }
}
