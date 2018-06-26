package fr.arthb.motherrussia.dto;

public class UserSessionDTO {
    private int id;
    private String login;
    private String name;
    private int gain;


    public UserSessionDTO(int id, String login, String name) {
        super();
        this.id = id;
        this.login = login;
        this.name = name;
        this.gain = gain;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getGain() {
        return this.gain;
    }

    public void setGain(int gain) {
        this.gain = gain;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
