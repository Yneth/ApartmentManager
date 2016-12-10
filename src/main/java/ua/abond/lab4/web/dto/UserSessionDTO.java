package ua.abond.lab4.web.dto;

import ua.abond.lab4.domain.Authority;
import ua.abond.lab4.domain.User;

import java.io.Serializable;

public class UserSessionDTO implements Serializable {
    private final Long id;
    private final String login;
    private final String password;
    private final Authority authority;

    public UserSessionDTO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.authority = user.getAuthority();
    }

    public UserSessionDTO(Long id, String login, String password, Authority authority) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Authority getAuthority() {
        return authority;
    }
}
