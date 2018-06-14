package com.yalafoot.bet.dto;

public class UserSessionDTO {
    private int identifier;
    private String login;
    private String name;


    public UserSessionDTO(int identifier, String login, String name) {
        super();
        this.identifier = identifier;
        this.login = login;
        this.name = name;
    }

    public String getId() {
        return this.login;
    }

    public void setId(int identifier) {
        this.identifier = identifier;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
