package ua.abond.lab4.domain;

import java.io.Serializable;
import java.util.Objects;

public class User extends Entity<Long> implements Serializable {
    private String firstName;
    private String lastName;

    private String login;
    private String password;

    private Authority authority;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User user = (User) o;
        return Objects.equals(getLogin(), user.getLogin()) &&
                Objects.equals(getAuthority(), user.getAuthority());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getAuthority());
    }
}
