package com.yalafoot.bet.dto;

import javax.validation.constraints.NotNull;

public class AuthDTO {

    @NotNull
    private String login;

    @NotNull
    private String password;

    private String passwordNew;

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

    public String getPasswordNew() {
        return passwordNew;
    }

    public void setPasswordNew(String passwordNew) {
        this.passwordNew = passwordNew;
    }
}
